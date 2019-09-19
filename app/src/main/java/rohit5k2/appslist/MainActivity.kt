package rohit5k2.appslist

import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class MainActivity : AppCompatActivity() {

    private val fileName:String = "rohit5k2.appslist.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        showSnackBar("Please grant permissions manually in settings if device Android version is 6 or above.")
        get_all_apps_info.setOnClickListener {
            getAppsList()
        }
    }

    private fun getAppsList(){
        val packs = packageManager.getInstalledPackages(0)
        var sb = StringBuilder()

        for (pi:PackageInfo  in packs){
            sb.append(pi.packageName + "\t\t" + pi.versionName + "\n\n" )
        }

        writeToFile(sb)
    }

    private fun writeToFile(sb:StringBuilder){
        try {
            var file = File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), fileName)
            if (!file!!.exists())
                file?.createNewFile()

            var buffer = BufferedWriter(FileWriter(file, false))

            buffer.write("***** Rohit Kumar - Device Apps list ***** \n\n\n\n")
            buffer.write(sb.toString())
            buffer.newLine()
            buffer.close()

            showSnackBar("File written in Download directory with name \"$fileName\"")
        } catch (e: Exception) {
            showSnackBar("An error occurred while writing the file.")
            e.printStackTrace()
        }
    }

    private fun showSnackBar(message:String){
        var sb: Snackbar = Snackbar.make(findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
        sb.setAction("Ok"){ }
        sb.show()
    }
}
