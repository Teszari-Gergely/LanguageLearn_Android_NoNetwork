package com.triolingo

import android.os.Bundle
import android.provider.Telephony.Mms.Part.TEXT
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.triolingo.dataTypes.WordPair
import com.triolingo.databinding.ActivityWordLearnBinding

class WordLearnActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityWordLearnBinding
    private var isMinusPoints: String = ""
    private var points = 0
    private var localList = mutableListOf<WordPair>()
    private var currentWordPair = WordPair(0, "", "", 0, null)
    private var currentIndex : Int = 0
    private lateinit var buttonsList : MutableList<Button>
    private lateinit var textViewsList : MutableList<TextView>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityWordLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences("isMinus", MODE_PRIVATE)
        isMinusPoints = sp.getString(TEXT, "")!!

        buttonsList = mutableListOf(binding.btnN1, binding.btnN2, binding.btnN3, binding.btnN4, binding.btnN5)
        textViewsList = mutableListOf(binding.twF1, binding.twF2, binding.twF3, binding.twF4, binding.twF5)

        if (isMinusPoints == "false") binding.twPoints.isVisible = false

        getWords()
        setEnvironment()
        refreshPointTW()
        currentIndex = 0
        localList = localList.shuffled().toMutableList()
        currentWordPair = localList[currentIndex]
        refreshCurrentWordTW()

        binding.btnN1.setOnClickListener { buttonHandler(1) }
        binding.btnN2.setOnClickListener { buttonHandler(2) }
        binding.btnN3.setOnClickListener { buttonHandler(3) }
        binding.btnN4.setOnClickListener { buttonHandler(4) }
        binding.btnN5.setOnClickListener { buttonHandler(5) }
        binding.btnGetNewSet.setOnClickListener {
            if (localList.isNotEmpty())
            {
                localList.clear()
            }
            getWords()
            setEnvironment()
            refreshPointTW()
            currentIndex = 0
            localList = localList.shuffled().toMutableList()
            currentWordPair = localList[currentIndex]
            refreshCurrentWordTW()
        }
    }

    private fun getWords() {}

    private fun setEnvironment()
    {
        if (localList.isEmpty() || localList.size != 5)
        {
            /* initializing the array for a burnt-in dataset */
            localList.clear()
            localList = mutableListOf(
                WordPair(1, "Aut??", "Car", 0, null),
                WordPair(1, "Villamos", "Tram", 0, null),
                WordPair(1, "Busz", "Bus", 0, null),
                WordPair(1, "H??V", "Suburban railway", 0, null),
                WordPair(1, "Metr??", "Subway", 0, null),
                WordPair(1, "Bicikli", "Bike", 0, null),
                WordPair(1, "Roller", "Scooter", 0, null),
                WordPair(1, "G??rkorcsolya", "Roller skates", 0, null),
                WordPair(1, "Troli", "Trolley", 0, null),
                WordPair(1, "Helikopter", "Helicopter", 0, null),
                WordPair(1, "Robog??", "Scooter", 0, null),
                WordPair(1, "G??rdeszka", "Skateboard", 0, null),
                WordPair(1, "Haj??", "Boat", 0, null),
                WordPair(1, "L??", "Horse", 0, null),
                WordPair(1, "L??bbusz", "Foot walk", 0, null),
                WordPair(1, "Rep??l??g??p", "Aeroplane", 0, null),
                WordPair(1, "Taxi", "Cab", 0, null),
                WordPair(1, "Fogaskerek??", "Cog-rail", 0, null),
                WordPair(1, "Libeg??", "Chairlift", 0, null),
                WordPair(1, "Sikl??", "Hill Funicular", 0, null),
                WordPair(1, "??tterem", "Restaurant", 0, null),
                WordPair(1, "K??v??z??", "Cafe", 0, null),
                WordPair(1, "P??ks??g", "Bakery", 0, null),
                WordPair(1, "Cukr??szda", "confectionery", 0, null),
                WordPair(1, "Kocsma", "Pub", 0, null),
                WordPair(1, "Bev??s??r?? k??zpont", "Shopping center", 0, null),
                WordPair(1, "Sz??nh??z", "Theatre", 0, null),
                WordPair(1, "Mozi", "Cinema", 0, null),
                WordPair(1, "M??zeum", "Museum", 0, null),
                WordPair(1, "Ki??ll??t??s", "Exhibition", 0, null),
                WordPair(1, "Park", "Park", 0, null),
                WordPair(1, "Vid??mpark", "Amusement park", 0, null),
                WordPair(1, "Piros", "Red", 0, null),
                WordPair(1, "Z??ld", "Green", 0, null),
                WordPair(1, "K??k", "Blue", 0, null),
                WordPair(1, "Lila", "Purple", 0, null),
                WordPair(1, "Narancs s??rga", "Orange", 0, null),
                WordPair(1, "S??rga", "Yellow", 0, null),
                WordPair(1, "Ci??nk??k", "Cyan", 0, null),
                WordPair(1, "Barna", "Brown", 0, null),
                WordPair(1, "Feh??r", "White", 0, null),
                WordPair(1, "Fekete", "Black", 0, null),
                WordPair(1, "Sz??rke", "Gray", 0, null),
                WordPair(1, "R??zsasz??n", "Pink", 0, null),
                WordPair(1, "??tl??tsz??", "Transparent", 0, null),
                WordPair(1, "Magyar", "English", 0, null),
            ).shuffled().toMutableList().subList(0, 5)
        }

        for (iter in localList)
        {
            buttonsList[localList.indexOf(iter)].text = iter.local
            buttonsList[localList.indexOf(iter)].isClickable = true
            buttonsList[localList.indexOf(iter)].isVisible = true

            textViewsList[localList.indexOf(iter)].text = iter.native
            textViewsList[localList.indexOf(iter)].isVisible = false
        }
    }

    private fun refreshPointTW()
    {
        binding.twPoints.text = "Points: $points"
    }
    private fun refreshCurrentWordTW()
    {
        binding.twCurrentWord.text = currentWordPair.native
    }

    private fun buttonHandler(num: Int)
    {
        if (buttonsList[num-1].text == currentWordPair.local)
        {
            textViewsList[num-1].isVisible = true
            if (isMinusPoints == "true")
            {
                buttonsList[num-1].isVisible = false
            }
            buttonsList[num-1].isClickable = false
            points++
            //wordListIndexIncrementer(false)
            currentIndex++
            currentWordPair = if (currentIndex > 4)
            {
                WordPair(0, "", "", 0, null)
            }
            else
            {
                localList[currentIndex]
            }
        }
        else
        {
            if (points > 0 && isMinusPoints == "true" ) points--
        }
        refreshPointTW()
        refreshCurrentWordTW()
    }
}