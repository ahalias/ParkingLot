import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess
import java.awt.Color
import java.util.Scanner



fun imgProperties(imgName: String, img: BufferedImage) {
    println("Image file: $imgName")
    println("Width: ${img.width}")
    println("Height: ${img.height}")
    println("Number of components: ${img.colorModel.numComponents}")
    println("Number of color components: ${img.colorModel.numColorComponents}")
    println("Bits per pixel: ${img.colorModel.pixelSize}")
    if (img.colorModel.pixelSize == 24) { println("Transparency: OPAQUE") } else { println("Transparency: TRANSLUCENT") }
}

    fun inputImageCheck(img: BufferedImage) {
        if (img.colorModel.numColorComponents !=3) {
            println("The number of image color components isn't 3.")
            exitProcess(0)
        }
        if (img.colorModel.pixelSize != 24 && img.colorModel.pixelSize != 32) {
            println("The image isn't 24 or 32-bit.")
            exitProcess(1)
        }
    }

fun waterMarkCheck(wm: BufferedImage) {
        if (wm.colorModel.numColorComponents !=3) {
            println("The number of watermark color components isn't 3.")
            exitProcess(2)
        }
        if (wm.colorModel.pixelSize != 24 && wm.colorModel.pixelSize != 32) {
            println("The watermark isn't 24 or 32-bit.")
            exitProcess(3)
        }
    }

fun compareDimensions(img: BufferedImage, wm: BufferedImage) {
    if (img.height != wm.height || img.width != wm.width) {
        println("The image and watermark dimensions are different.")
        exitProcess(4)
    }
}



fun checkTransparencyPerc(weight1: String) {
    try {
        weight1.toInt()
    } catch (e: Exception) {
        println("The transparency percentage isn't an integer number.")
        exitProcess(7)
    }
    if (weight1.toInt() < 0 || weight1.toInt() > 100) {
        println("The transparency percentage is out of range.")
        exitProcess(8)
    }
}

fun outputImgExt(format: String) {
    if (format.substringAfterLast(".") != "jpg" && format.substringAfterLast(".") != "png") {
        println("The output file extension isn't \"jpg\" or \"png\".")
        exitProcess(9)
    }
}

fun blendImages(img: BufferedImage, wm: BufferedImage, weight: Int, outputBI: BufferedImage, outputImg: String, format: String, trAnswer: String, trColorR: Int, trColorG: Int, trColorB: Int, trSetAnswer: String) {
    for (x in 0 until img.width) {
        for (y in 0 until img.height) {
            if (trAnswer == "yes") {
                val w = Color(wm.getRGB(x, y), true)
                if (w.alpha == 0) {
                    outputBI.setRGB(x, y, Color(img.getRGB(x, y)).rgb)
                } else {
                    val i = Color(img.getRGB(x, y))
                    val color = Color(
                        (weight * w.red + (100 - weight) * i.red) / 100,
                        (weight * w.green + (100 - weight) * i.green) / 100,
                        (weight * w.blue + (100 - weight) * i.blue) / 100
                    )
                    outputBI.setRGB(x, y, color.rgb)
                }
            } else if (trSetAnswer == "yes") {
                val i = Color(img.getRGB(x, y))
                val w = Color(wm.getRGB(x, y))
                val color1 = Color(
                    (trColorR),
                    (trColorG),
                    (trColorB)
                )
                if (w == color1) {
                    outputBI.setRGB(x, y, Color(img.getRGB(x, y)).rgb)

                } else {
                    val color = Color(
                        (weight * w.red + (100 - weight) * i.red) / 100,
                        (weight * w.green + (100 - weight) * i.green) / 100,
                        (weight * w.blue + (100 - weight) * i.blue) / 100
                    )
                    outputBI.setRGB(x, y, color.rgb)
                }

            } else {
            val i = Color(img.getRGB(x, y))
            val w = Color(wm.getRGB(x, y))
            val color = Color(
                (weight * w.red + (100 - weight) * i.red) / 100,
                (weight * w.green + (100 - weight) * i.green) / 100,
                (weight * w.blue + (100 - weight) * i.blue) / 100
            )
            outputBI.setRGB(x, y, color.rgb)
        }
    }
    }
    val saveImg = File(outputImg)
    ImageIO.write(outputBI, format, saveImg)
    println("The watermarked image $saveImg has been created.")

}

fun main() {
    println("Input the image filename: ")
    val imgName = readln()
    val imgFile = File(imgName)
    if (!imgFile.exists()) {
        println("The file $imgName doesn't exist.")
        exitProcess(5)
    }
    val img = ImageIO.read(imgFile)

    inputImageCheck(img)

    println("Input the watermark image filename: ")
    val waterMarkName = readln()
    val waterM = File(waterMarkName)
    if (!waterM.exists()) {
        println("The file $waterMarkName doesn't exist.")
        exitProcess(11)
    }
    val wm: BufferedImage = ImageIO.read(waterM)

    waterMarkCheck(wm)

    compareDimensions(img, wm)

    var trAnswer = "0"
    var trSetAnswer = "0"
    if (wm.transparency == 3) {
        println("Do you want to use the watermark's Alpha channel?")
        trAnswer = readln().lowercase()
    } else {
        println("Do you want to set a transparency color?")
        trSetAnswer = readln().lowercase()
    }
    val scanner = Scanner(System.`in`)
    var trColorR = 0
    var trColorG = 0
    var trColorB = 0
    if (trSetAnswer == "yes") {
        println("Input a transparency color ([Red] [Green] [Blue]):")
        try {
            val listA = readln().split(" "). map { it.toInt() }
            trColorR = listA[0]
            trColorG = listA[1]
            trColorB = listA[2]
            val range = 0..255
            if (trColorR !in range || trColorG !in range || trColorB !in range) {
                println("The transparency color input is invalid.")
                System.exit(0)
            } else if (listA.size != 3) {
                println("The transparency color input is invalid.")
                System.exit(0)
            }
        } catch (e: Exception) {
                println("The transparency color input is invalid.").also { exitProcess(-1) }
            }

        }
        println("Input the watermark transparency percentage (Integer 0-100): ")
        val weight1 = readln()

        checkTransparencyPerc(weight1)

        println("Input the output image filename (jpg or png extension): ")
        val outputImg = readln()
        val format = outputImg.substringAfterLast(".")

        outputImgExt(format)

        val weight = weight1.toInt()
        val outputBI = BufferedImage(img.width, img.height, BufferedImage.TYPE_INT_RGB)
        blendImages(img, wm, weight, outputBI, outputImg, format, trAnswer, trColorR, trColorG, trColorB, trSetAnswer)

    }