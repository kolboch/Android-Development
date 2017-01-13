package dev.kb.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int pointsTeamA;
    private int pointsTeamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointsTeamA = 0;
        pointsTeamB = 0;
    }
    /**
     * adds specifed points (from android:tag in view) to teamA score and updates View
     * @param view
     */
    public void addPointsTeamA(View view){
        String pointsS = view.getTag().toString();
        int points = Integer.parseInt(pointsS);
        pointsTeamA += points;
        updateTeamAScore();
    }

    /**
     * adds specifed points (from android:tag in view) to teamB score and updates View
     * @param view
     */
    public void addPointsTeamB(View view){
        String pointsS = view.getTag().toString();
        int points = Integer.parseInt(pointsS);
        pointsTeamB += points;
        updateTeamBScore();
    }

    /**
     * reset points and scoreboards in view
     */
    public void resetPointsAndUpdate(View view) {
        pointsTeamA = 0;
        pointsTeamB = 0;
        updateBothTeams();
    }

    /**
     * updates both teams scoreboards
     */
    private void updateBothTeams() {
        updateTeamAScore();
        updateTeamBScore();
    }

    /**
     * updates teamA scoreboard
     */
    private void updateTeamAScore() {
        TextView teamA = (TextView) findViewById(R.id.teamAScore);
        teamA.setText("" + pointsTeamA);
    }

    /**
     * updates teamB scoreboard
     */
    private void updateTeamBScore() {
        TextView teamA = (TextView) findViewById(R.id.teamBScore);
        teamA.setText("" + pointsTeamB);
    }
}
