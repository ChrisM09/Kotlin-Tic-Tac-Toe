package com.example.kotlintictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var buttons = ArrayList<Button>()

    // List of buttons owned by a player
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    var activePlayer = 1

    var numberOfAvailableButtons = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the array of buttons
        for(i in 0..8)
        {
            val btn_id = "btn_" + i
            val resourceID = resources.getIdentifier(btn_id, "id", packageName)
            buttons.add(findViewById<Button>(resourceID))
        }


        resetGame()
    }

    fun buttonClick(view: View)
    {
        val buttonSelected = view as Button
        var cellID = 0

        // Switch statement in Kotlin
        when(buttonSelected.id)
        {
            // Lambda function to define what to do when it's a specific button
            R.id.btn_0 -> cellID = 0
            R.id.btn_1 -> cellID = 1
            R.id.btn_2 -> cellID = 2
            R.id.btn_3 -> cellID = 3
            R.id.btn_4 -> cellID = 4
            R.id.btn_5 -> cellID = 5
            R.id.btn_6 -> cellID = 6
            R.id.btn_7 -> cellID = 7
            R.id.btn_8 -> cellID = 8
        }

        //Toast.makeText(this, "Cell ID: " + cellID, Toast.LENGTH_SHORT).show()
        playGame(cellID, buttonSelected)

    }


    private fun playGame(cellID: Int, buttonSelected: Button)
    {
        if(activePlayer == 1)
        {
            buttonSelected.text = "X"
            buttonSelected.setBackgroundColor(Color.parseColor("#009193"))
            player1.add(cellID)
            activePlayer = 2
        }
        else
        {
            buttonSelected.text = "O"
            buttonSelected.setBackgroundColor(Color.parseColor("#FF9300"))
            player2.add(cellID)
            activePlayer = 1
        }
        buttonSelected.isEnabled = false


        if(activePlayer == 2)
            checkWinner(1);
        else
            checkWinner(2)


    }

    private fun checkWinner(lastPlayed: Int)
    {
        var winner = -1
        var copyOfLastPlayersArray = ArrayList<Int>()

        // Find which array to look at based on the current player.
        if(lastPlayed == 1)
            copyOfLastPlayersArray.addAll(player1)
        else
            copyOfLastPlayersArray.addAll(player2)

        // (0,1,2)
        // (3,4,5)
        // (6,7,8)

        if( copyOfLastPlayersArray.contains(0) && copyOfLastPlayersArray.contains(1) && copyOfLastPlayersArray.contains(2) ||
            copyOfLastPlayersArray.contains(3) && copyOfLastPlayersArray.contains(4) && copyOfLastPlayersArray.contains(5) ||
            copyOfLastPlayersArray.contains(6) && copyOfLastPlayersArray.contains(7) && copyOfLastPlayersArray.contains(8) ||
            copyOfLastPlayersArray.contains(0) && copyOfLastPlayersArray.contains(3) && copyOfLastPlayersArray.contains(6) ||
            copyOfLastPlayersArray.contains(1) && copyOfLastPlayersArray.contains(4) && copyOfLastPlayersArray.contains(7) ||
            copyOfLastPlayersArray.contains(2) && copyOfLastPlayersArray.contains(5) && copyOfLastPlayersArray.contains(8) ||
            copyOfLastPlayersArray.contains(0) && copyOfLastPlayersArray.contains(4) && copyOfLastPlayersArray.contains(8) ||
            copyOfLastPlayersArray.contains(2) && copyOfLastPlayersArray.contains(4) && copyOfLastPlayersArray.contains(6))
        {
            winner = lastPlayed
        }

        if(winner != -1)
        {
            if(winner == 1)
            {
                Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show()
            }

            updatePlayerScore(winner)

            // reset the board to go another round.
            resetBoard()
        }
        else
        {
            numberOfAvailableButtons -= 1
            if (numberOfAvailableButtons <= 0) {
                Toast.makeText(this, "Tie Game!", Toast.LENGTH_SHORT).show()
                resetBoard()
            }
        }
    }

    private fun resetBoard()
    {
        // Clear out all the text for the buttons and enable them to be clicked
        for(button in buttons)
        {
            button.isEnabled = true
            button.text = ""
            button.setBackgroundColor(Color.parseColor("#413F43"))
        }
        // reset owned arrays
        player1.clear()
        player2.clear()

        // reset player
        activePlayer = 1

        // reset number of available buttons
        numberOfAvailableButtons = 9

    }

    private fun updatePlayerScore(winner: Int)
    {
        val playerOneScoreTextView = findViewById<TextView>(R.id.playerOneScore)
        var p1Score = Integer.parseInt(playerOneScoreTextView.text.toString())

        val playerTwoScoreTextView = findViewById<TextView>(R.id.playerTwoScore)
        var p2Score = Integer.parseInt(playerTwoScoreTextView.text.toString())

        if(winner == 1)
        {
            p1Score += 1
            playerOneScoreTextView.text = (p1Score).toString()

        }
        else
        {
            p2Score += 1
            playerTwoScoreTextView.text = (p2Score).toString()
        }

        // check two scores and update the player status accordingly
        val playerStatusView = findViewById<TextView>(R.id.playerStatus)
        if (p1Score > p2Score)
            playerStatusView.text = "Player 1 is winning!"
        else if (p2Score > p1Score)
            playerStatusView.text = "Player 2 is winning!"
        else
            playerStatusView.text = "Tie Game!"

    }

    private fun resetGame()
    {
        // set the current player back to 1
        activePlayer = 1

        // set all the scores back to the start.
        val playerOneScoreTextView = findViewById<TextView>(R.id.playerOneScore)
        playerOneScoreTextView?.setText("0").toString()

        val playerTwoScoreTextView = findViewById<TextView>(R.id.playerTwoScore)
        playerTwoScoreTextView?.setText("0").toString()

        val playerStatusTextView = findViewById<TextView>(R.id.playerStatus)
        playerStatusTextView?.setText("Tie Game").toString()


        resetBoard()
    }

    fun resetGame(view: View)
    {
        resetGame()
    }
}