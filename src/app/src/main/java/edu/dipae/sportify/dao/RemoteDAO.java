package edu.dipae.sportify.dao;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import edu.dipae.sportify.models.Match;

public class RemoteDAO
{
    FirebaseFirestore db;

    public RemoteDAO(){
         db = FirebaseFirestore.getInstance();
    }

    public ArrayList<Match> getAllSports() {
        // TODO replace with actual query
        return null;
    }

    public Match insertMatch(Match match)
    {
        CollectionReference agones = db.collection("agones");
        agones.add(match);

        return null;
    }
}
