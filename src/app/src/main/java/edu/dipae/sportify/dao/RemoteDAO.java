package edu.dipae.sportify.dao;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import edu.dipae.sportify.models.Match;

public class RemoteDAO
{
    FirebaseFirestore db;

    public RemoteDAO(){
         db = FirebaseFirestore.getInstance();
    }

    public ArrayList<Match> getAllMatches() throws ExecutionException, InterruptedException
    {
        CollectionReference agones = db.collection("agones");
        Task<QuerySnapshot> task = agones.orderBy("date").get();
        QuerySnapshot querySnapshot = Tasks.await(task);

        return new ArrayList<>(querySnapshot.toObjects(Match.class));
    }

    public Match insertMatch(Match match) throws ExecutionException, InterruptedException
    {
        CollectionReference agones = db.collection("agones");
        Task<DocumentReference> insertTask = agones.add(match);

        DocumentReference result = Tasks.await(insertTask);

        // Firestore does not add a id field so we have ot update it manually
        match.setId(result.getId());
        return updateMatch(match);
    }

    public Match updateMatch(Match match) throws ExecutionException, InterruptedException
    {
        CollectionReference agones = db.collection("agones");
        Task<Void> task = agones.document("" + match.getId()).set(match);
        Tasks.await(task);

        return match;
    }

    public Match getMatchById(String matchId) throws ExecutionException, InterruptedException {
        CollectionReference agones = db.collection("agones");
        DocumentReference documentReference = agones.document("" + matchId);
        Task<DocumentSnapshot> task = documentReference.get();
        DocumentSnapshot documentSnapshot = Tasks.await(task);

        return documentSnapshot.toObject(Match.class);
    }

    public void deleteMatch(String matchId) throws ExecutionException, InterruptedException {
        CollectionReference agones = db.collection("agones");
        DocumentReference documentReference = agones.document("" + matchId);
        Task<Void> task = documentReference.delete();
        Tasks.await(task);
    }

    public ArrayList<Match> getMatchBySportId(int sportId) throws ExecutionException, InterruptedException {
        CollectionReference agones = db.collection("agones");
        Task<QuerySnapshot> task = agones
                .orderBy("date")
                .whereEqualTo("sportId", sportId)
                .get();
        QuerySnapshot querySnapshot = Tasks.await(task);

        return new ArrayList<>(querySnapshot.toObjects(Match.class));
    }
}
