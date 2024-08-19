package com.example.globaldirectory.utils

import android.content.res.AssetManager
import org.json.JSONObject

class LoadJSONFile {

    private fun loadJSONfromAssets(name: String, assets: AssetManager): String {

        return try {
            // Begins a try block to catch any IO exceptions that may occur.

            val inputStream = assets.open(name)
            // Opens the specified file from the assets using AssetManager, returning an InputStream.

            val size = inputStream.available()
            // Gets the size of the file (in bytes) using the available() method.

            val buffer = ByteArray(size)
            // Creates a byte array (buffer) with the same size as the file.

            inputStream.read(buffer)
            // Reads the file's content into the buffer array.

            inputStream.close()
            // Closes the InputStream to free up resources.

            String(buffer, charset("UTF-8"))
            // Converts the buffer (byte array) to a String using UTF-8 encoding, and returns it.

        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            ""
            // Returns an empty string if an error occurred.
        }
    }

    fun loadJSONfromString(name: String, asset: AssetManager): JSONObject {
        return try {
            JSONObject(this.loadJSONfromAssets(name, asset))
            // Calls the private method loadJSONfromAssets to get the JSON content as a string.
            // Converts the string to a JSONObject and returns it.

        } catch (ex: org.json.JSONException) {
            ex.printStackTrace()
            JSONObject()
            // Returns an empty JSONObject if an error occurred.
        }
    }
}
