package com.example.listviewmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {


    private lateinit var listview: ListView
    private var ordi_id = ArrayList<Int>()
    private var ordi_name = ArrayList<String>()
    private var ordi_price = ArrayList<Double>()
    private var ordi_image = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listview = findViewById(R.id.listView)

        /*************************************************************/
        val db = DataBase(applicationContext)
        val ordiList = db.getAllOrdi()

        ordi_id.clear()
        ordi_name.clear()
        ordi_price.clear()
        ordi_image.clear()

        for (ordi in ordiList) {
            ordi_id.add(ordi.id)
            ordi_name.add(ordi.name)
            ordi_price.add(ordi.price)
            ordi_image.add(ordi.image)
        }

        // listview = findViewById(R.id.listView)
        listview.adapter = Ressources_ordi()

        /***************************************************/

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        /**********************************************************/

        buttonAdd.setOnClickListener {

            findViewById<EditText>(R.id.editTextId).isEnabled = true

            val editTextId = findViewById<EditText>(R.id.editTextId).text.toString().trim()
            val editTextName = findViewById<EditText>(R.id.editTextName).text.toString().trim()
            val editTextPrice = findViewById<EditText>(R.id.editTextPrice).text.toString().trim()
            val editTextImageLink =
                findViewById<EditText>(R.id.editTextImageURL).text.toString().trim()


            if (editTextId.isEmpty() || editTextName.isEmpty() || editTextPrice.isEmpty()
                || editTextImageLink.isEmpty()
            ) {

                if (editTextId.isEmpty()) {
                    findViewById<EditText>(R.id.editTextId).setBackgroundColor(
                        resources.getColor(R.color.alertColor)
                    )
                } else {
                    findViewById<EditText>(R.id.editTextId).setBackgroundColor(
                        resources.getColor(R.color.principaleColor)
                    )
                }
                if (editTextName.isEmpty()) {
                    findViewById<EditText>(R.id.editTextName).setBackgroundColor(
                        resources.getColor(R.color.alertColor)
                    )
                } else {
                    findViewById<EditText>(R.id.editTextName).setBackgroundColor(
                        resources.getColor(R.color.principaleColor)
                    )
                }
                if (editTextPrice.isEmpty()) {
                    findViewById<EditText>(R.id.editTextPrice).setBackgroundColor(
                        resources.getColor(R.color.alertColor)
                    )
                } else {
                    findViewById<EditText>(R.id.editTextPrice).setBackgroundColor(
                        resources.getColor(R.color.principaleColor)
                    )
                }
                if (editTextImageLink.isEmpty()) {
                    findViewById<EditText>(R.id.editTextImageURL).setBackgroundColor(
                        resources.getColor(R.color.alertColor)
                    )
                } else {
                    findViewById<EditText>(R.id.editTextImageURL).setBackgroundColor(
                        resources.getColor(R.color.principaleColor)
                    )
                }
            } else {

                findViewById<EditText>(R.id.editTextId).setBackgroundColor(
                    resources.getColor(R.color.principaleColor)
                )
                findViewById<EditText>(R.id.editTextName).setBackgroundColor(
                    resources.getColor(R.color.principaleColor)
                )
                findViewById<EditText>(R.id.editTextPrice).setBackgroundColor(
                    resources.getColor(R.color.principaleColor)
                )
                findViewById<EditText>(R.id.editTextImageURL).setBackgroundColor(
                    resources.getColor(R.color.principaleColor)
                )

                val ordi = Ordinateur(
                    editTextId.toInt(),
                    editTextName,
                    editTextPrice.toDouble(),
                    editTextImageLink
                )


                val db = DataBase(applicationContext)

                val resultat = db.addOrdi(ordi)
                if (resultat != (-1).toLong()) {
                    Toast.makeText(
                        applicationContext,
                        "Ordinateur Succesfuly Added",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_LONG)
                        .show()
                }

            }

            /***************************************************/

            val db = DataBase(applicationContext)
            val ordiList = db.getAllOrdi()

            ordi_id.clear()
            ordi_name.clear()
            ordi_price.clear()
            ordi_image.clear()

            for (ordi in ordiList) {
                ordi_id.add(ordi.id)
                ordi_name.add(ordi.name)
                ordi_price.add(ordi.price)
                ordi_image.add(ordi.image)
            }

            listview.adapter = Ressources_ordi()

            /****************************************************/

        }

        buttonSearch.setOnClickListener {
            findViewById<EditText>(R.id.editTextId).isEnabled = true
            var idString = findViewById<EditText>(R.id.editTextId).text.toString().trim()
            if (idString.isEmpty()) {
                findViewById<EditText>(R.id.editTextId).setBackgroundColor(resources.getColor(R.color.alertColor))
                Toast.makeText(
                    applicationContext,
                    "Veuillez entrer une valeur numérique valide pour l'id",
                    Toast.LENGTH_LONG
                ).show()
                /***************************************************/

                val db = DataBase(applicationContext)
                val ordiList = db.getAllOrdi()

                ordi_id.clear()
                ordi_name.clear()
                ordi_price.clear()
                ordi_image.clear()

                for (ordi in ordiList) {
                    ordi_id.add(ordi.id)
                    ordi_name.add(ordi.name)
                    ordi_price.add(ordi.price)
                    ordi_image.add(ordi.image)
                }

                /***************************************************/

            } else {
                findViewById<EditText>(R.id.editTextId).setBackgroundColor(resources.getColor(R.color.principaleColor))
                try {
                    val id = idString.toInt()

                    val db = DataBase(applicationContext)
                    val ordiListItem = db.searchById(id)
                    if (ordiListItem.size == 0) {
                        Toast.makeText(
                            applicationContext,
                            "L'id $id n'existe pas !",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {

                        ordi_id.clear()
                        ordi_name.clear()
                        ordi_price.clear()
                        ordi_image.clear()

                        for (ordi in ordiListItem) {

                            ordi_id.add(ordi.id)
                            ordi_name.add(ordi.name)
                            ordi_price.add(ordi.price)
                            ordi_image.add(ordi.image)
                        }
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        applicationContext,
                        "Veuillez entrer une valeur numérique valide pour l'id",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            /***************************************************/
            listview.adapter = Ressources_ordi()
            /****************************************************/
        }
        listview.setOnItemClickListener { parent, view, position, id ->

            /*********************************************************/
            val db = DataBase(applicationContext)
            val ordiList = db.getAllOrdi()

            ordi_id.clear()
            ordi_name.clear()
            ordi_price.clear()
            ordi_image.clear()

            for (ordi in ordiList) {
                ordi_id.add(ordi.id)
                ordi_name.add(ordi.name)
                ordi_price.add(ordi.price)
                ordi_image.add(ordi.image)
            }

            listview.adapter = Ressources_ordi()

            /********************************************************/

            val ordi = ordiList[position]
            val editTextId = findViewById<EditText>(R.id.editTextId)
            val editTextName = findViewById<EditText>(R.id.editTextName)
            val editTextPrice = findViewById<EditText>(R.id.editTextPrice)
            val editTextImageLink = findViewById<EditText>(R.id.editTextImageURL)

            editTextId.setText(ordi.id.toString())
            editTextName.setText(ordi.name)
            editTextPrice.setText(ordi.price.toString())
            editTextImageLink.setText(ordi.image)

            editTextId.isEnabled = false

            /*****************************************************/

            buttonDelete.setOnClickListener {

                var idString = findViewById<EditText>(R.id.editTextId).text.toString().trim()

                findViewById<EditText>(R.id.editTextId).setBackgroundColor(resources.getColor(R.color.principaleColor))
                try {
                    val id = idString.toInt()
                    val db = DataBase(applicationContext)
                    // Utilisation de la fonction deletePC pour supprimer un ordinateur avec l'ID 1
                    val rowsDeleted = db.deletePC(id)
                    if (rowsDeleted > 0) {
                        Toast.makeText(
                            applicationContext,
                            "L'ordinateur avec l'ID $id a été supprimé.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Aucun ordinateur n'a été supprimé.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }

                /***************************************************/

                val db = DataBase(applicationContext)
                val ordiList = db.getAllOrdi()

                ordi_id.clear()
                ordi_name.clear()
                ordi_price.clear()
                ordi_image.clear()

                for (ordi in ordiList) {
                    ordi_id.add(ordi.id)
                    ordi_name.add(ordi.name)
                    ordi_price.add(ordi.price)
                    ordi_image.add(ordi.image)
                }
                listview.adapter = Ressources_ordi()
            }

            /***************************************************************/

            buttonUpdate.setOnClickListener {

                val editTextId = findViewById<EditText>(R.id.editTextId).text.toString().trim()
                val editTextName = findViewById<EditText>(R.id.editTextName).text.toString().trim()
                val editTextPrice =
                    findViewById<EditText>(R.id.editTextPrice).text.toString().trim()
                val editTextImageLink =
                    findViewById<EditText>(R.id.editTextImageURL).text.toString().trim()

                val ordi = Ordinateur(
                    editTextId.toInt(),
                    editTextName,
                    editTextPrice.toDouble(),
                    editTextImageLink
                )
                val db = DataBase(applicationContext)
                val resultat = db.updateOrdi(ordi)
                if (resultat != (-1).toLong()) {
                    Toast.makeText(
                        applicationContext,
                        "Successfully Updated",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(applicationContext, "Update Failed", Toast.LENGTH_LONG)
                        .show()
                }

                /***************************************************/
                val ordiList = db.getAllOrdi()
                ordi_id.clear()
                ordi_name.clear()
                ordi_price.clear()
                ordi_image.clear()
                for (ordi in ordiList) {
                    ordi_id.add(ordi.id)
                    ordi_name.add(ordi.name)
                    ordi_price.add(ordi.price)
                    ordi_image.add(ordi.image)
                }

                listview.adapter = Ressources_ordi()

            }
        }

    }
    inner class Ressources_ordi : BaseAdapter() {
        override fun getCount(): Int = ordi_image.size

        override fun getItem(position: Int): Any? = null

        override fun getItemId(position: Int): Long = 0

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v: View
            val inflater = layoutInflater
            v = inflater.inflate(R.layout.layout_item, null)
            val name = v.findViewById<TextView>(R.id.textViewName)
            val price = v.findViewById<TextView>(R.id.textViewPrice)
            val img = v.findViewById<ImageView>(R.id.imageView)
            name.text = ordi_name[position]
            price.text = ordi_price[position].toString() + " MAD"
            Picasso.get().load(ordi_image[position]).centerCrop().resize(100, 100)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(img)

            return v
        }
    }
}