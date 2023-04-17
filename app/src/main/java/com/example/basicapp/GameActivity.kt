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

        val (row, col) = getButtonPosition(view)

        val player = if (player1Turn) 1 else 2
        val symbol = if (player1Turn) "X" else "O"

        view.text = symbol
        boardStatus[row][col] = player
        roundCount++

        if (checkForWin()) {
            announceWinner(player)
            return
        }

        if (roundCount == 9) {
            announceWinner(0)
            return
        }

        player1Turn = !player1Turn
    }

    private fun getButtonPosition(button: Button): Pair<Int, Int> {
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                if (button == buttons[i][j]) {
                    return Pair(i, j)
                }
            }
        }
        throw IllegalArgumentException("Button not found")
    }




    private fun checkForWin(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (boardStatus[i][0] != 0 && boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2]) {
                return true // row win
            }
            if (boardStatus[0][i] != 0 && boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i]) {
                return true // column win
            }
        }

        // Check diagonals
        if (boardStatus[1][1] != 0) {
            if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2]) {
                return true // diagonal win
            }
            if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0]) {
                return true // diagonal win
            }
        }

        return false // no win
    }



    private fun announceWinner(winningPlayer: Int) {
        if (winningPlayer == 0) {
            Toast.makeText(this, "Nobody wins! :(", Toast.LENGTH_SHORT).show()
        } else {
            val winningSymbol = if (winningPlayer == 1) "X" else "O"
            val winnerText = "Player $winningPlayer ($winningSymbol) wins!"
            Toast.makeText(this, winnerText, Toast.LENGTH_SHORT).show()
        }
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
