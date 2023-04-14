package com.example.basicapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity(), View.OnClickListener {
    private var player1Turn = true
    private var roundCount = 0
    private val boardStatus = Array(3) { IntArray(3) }
    private lateinit var buttons: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        buttons = arrayOf(
            arrayOf(findViewById(R.id.button_00), findViewById(R.id.button_01), findViewById(R.id.button_02)),
            arrayOf(findViewById(R.id.button_10), findViewById(R.id.button_11), findViewById(R.id.button_12)),
            arrayOf(findViewById(R.id.button_20), findViewById(R.id.button_21), findViewById(R.id.button_22))
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener(this)
            }
        }

        val resetButton: Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener {
            resetBoard()
        }
    }

    override fun onClick(view: View) {
        if ((view as Button).text.toString() != "") {
            return
        }

        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                if (view != buttons[i][j]) {
                    continue
                }

                val player = if (player1Turn) 1 else 2
                val symbol = if (player1Turn) "X" else "O"

                view.text = symbol
                boardStatus[i][j] = player
                roundCount++

                if (checkForWin()) {
                    val winnerText = "Player $player wins!"
                    announceWinner(winnerText)
                    return
                }

                if (roundCount == 9) {
                    announceWinner("It's a draw!")
                    return
                }

                player1Turn = !player1Turn
                return
            }
        }
    }



    private fun checkForWin(): Boolean {
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2] && boardStatus[i][0] != 0) {
                return true
            }
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i] && boardStatus[0][i] != 0) {
                return true
            }
        }
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2] && boardStatus[0][0] != 0) {
            return true
        }
        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0] && boardStatus[0][2] != 0) {
            return true
        }
        return false
    }

    private fun announceWinner(winnerText: String) {
        Toast.makeText(this, winnerText, Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                boardStatus[i][j] = 0
            }
        }
        player1Turn = true
        roundCount = 0
    }

}
