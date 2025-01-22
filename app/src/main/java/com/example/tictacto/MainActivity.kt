package com.example.tictacto

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var boardGame: ArrayList<ImageButton> = ArrayList(9)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setTitle("Tic Tac To")

        val board: LinearLayout = findViewById(R.id.linearLayout);
        for (i in 0 until 9)
            this.boardGame.add(board.findViewWithTag(i.toString()))

        this.boardGame.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener { onClick(imageButton) }
        }

        findViewById<Button?>(R.id.Activity2_reset).setOnClickListener {
            this.boardGame.forEachIndexed { index, imageButton ->
                imageButton.isClickable = true
                imageButton.setImageDrawable(null)
            }
            this.turn = true
            findViewById<TextView>(R.id.Activity2_TextWin).text = "X Play"
            findViewById<Button?>(R.id.Activity2_reset).visibility = View.GONE
            count = 0
        }
    }

    private var turn: Boolean = true
    var count: Int = 0

    @SuppressLint("SetTextI18n")
    private fun onClick(btn: ImageButton) {
        val flowGame: TextView = findViewById(R.id.Activity2_TextWin)
        if (btn.drawable != null) return


        if (this.turn) {
            btn.setImageResource(R.drawable.x)
            flowGame.text = "O Play"
        } else {
            btn.setImageResource(R.drawable.circle)
            flowGame.text = "X Play"
        }

        count++
        this.turn = !this.turn

        val isWin: Boolean = win()

        if ((count == 9) && !isWin) {
            flowGame.text = "Draw"
            findViewById<Button?>(R.id.Activity2_reset).visibility = View.VISIBLE
            return
        }

        if (isWin) endOfGame(!turn)
    }

    private fun win(): Boolean {
        val rowsWin = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        for (row in rowsWin) {
            val state1 = this.boardGame[row[0]].drawable?.constantState
            val state2 = this.boardGame[row[1]].drawable?.constantState
            val state3 = this.boardGame[row[2]].drawable?.constantState
            if (state1 != null && state1 == state2 && state2 == state3)
                return true;
        }

        return false;
    }

    @SuppressLint("SetTextI18n")
    private fun endOfGame(win: Boolean) {
        findViewById<Button?>(R.id.Activity2_reset).visibility = View.VISIBLE
        val flowGame: TextView = findViewById(R.id.Activity2_TextWin)

        if (win)
            flowGame.text = "X is Win"
        else
            flowGame.text = "O is Win"

        this.boardGame.forEachIndexed { index, imageButton ->
            imageButton.isClickable = false
        }
    }

}