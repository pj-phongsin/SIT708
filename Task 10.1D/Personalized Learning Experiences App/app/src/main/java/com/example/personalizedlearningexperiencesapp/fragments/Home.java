package com.example.personalizedlearningexperiencesapp.fragments;
import android.widget.Button;
import com.example.personalizedlearningexperiencesapp.fragments.ProfileFragment;
import com.example.personalizedlearningexperiencesapp.fragments.HistoryFragment;
import com.example.personalizedlearningexperiencesapp.fragments.UpgradeFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.personalizedlearningexperiencesapp.MainActivity;
import com.example.personalizedlearningexperiencesapp.R;
import com.example.personalizedlearningexperiencesapp.model.QuizResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends Fragment {

    private RequestQueue requestQueue;
    private ArrayList<QuizResponse.Question> quizList;
    private CardView generatedTaskCard;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView yourNameTextView = view.findViewById(R.id.textView7);

        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("username", "Your Name");
            yourNameTextView.setText(username);
        } else {
            yourNameTextView.setText("Your Name");
        }

        // Initialize navigation buttons immediately after inflating the view
        Button profileButton = view.findViewById(R.id.btnProfile);
        Button historyButton = view.findViewById(R.id.btnHistory);
        Button upgradeButton = view.findViewById(R.id.btnUpgrade);

        if (args != null) {
            String username = args.getString("username");

            profileButton.setOnClickListener(v -> {
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                profileFragment.setArguments(bundle);
                ((MainActivity) getActivity()).switchFragment(profileFragment);
            });

            historyButton.setOnClickListener(v -> {
                HistoryFragment historyFragment = new HistoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                historyFragment.setArguments(bundle);
                ((MainActivity) getActivity()).switchFragment(historyFragment);
            });

            upgradeButton.setOnClickListener(v -> {
                UpgradeFragment upgradeFragment = new UpgradeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                upgradeFragment.setArguments(bundle);
                ((MainActivity) getActivity()).switchFragment(upgradeFragment);
            });
        }

        requestQueue = Volley.newRequestQueue(getContext());
        generatedTaskCard = view.findViewById(R.id.generatedTaskCard);
        TextView generatedTaskTitle = view.findViewById(R.id.generatedTaskTitle);
        TextView generatedTaskDescription = view.findViewById(R.id.generatedTaskDescription);

        generatedTaskCard.setOnClickListener(v -> onGeneratedTaskClicked());

        if (args != null) {
            ArrayList<String> selectedTopics = args.getStringArrayList("selectedTopics");
            if (selectedTopics != null && !selectedTopics.isEmpty()) {
                String topicString = String.join(",", selectedTopics);

                generatedTaskCard.setVisibility(View.VISIBLE);
                generatedTaskTitle.setText("Generated Task 1");
                generatedTaskDescription.setText(topicString);

                fetchQuizFromServer(topicString);
            } else {
                Toast.makeText(getContext(), "No topics selected", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    private void onGeneratedTaskClicked() {
        if (quizList != null && !quizList.isEmpty()) {
            Quiz quizFragment = new Quiz();
            Bundle bundle = new Bundle();
            bundle.putSerializable("quizData", quizList);
            if (getArguments() != null) {
                String username = getArguments().getString("username", "guest");
                bundle.putString("username", username);
            }
            quizFragment.setArguments(bundle);

            ((MainActivity) getActivity()).switchFragment(quizFragment);
        } else {
            Toast.makeText(getContext(), "Quiz not loaded yet. Please wait...", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchQuizFromServer(String topic) {
        String url = "http://10.0.2.2:5001/getQuiz?topic=" + topic;
        Log.d("FETCH_URL", "Fetching from: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("FETCH_RESPONSE", "Response: " + response.toString());

                    try {
                        JSONArray quizArray = response.getJSONArray("quiz");
                        quizList = new ArrayList<>();

                        for (int i = 0; i < quizArray.length(); i++) {
                            JSONObject quizObject = quizArray.getJSONObject(i);
                            String questionText = quizObject.getString("question");
                            JSONArray optionsArray = quizObject.getJSONArray("options");
                            String correctAnswer = quizObject.getString("correct_answer");

                            ArrayList<String> optionsList = new ArrayList<>();
                            for (int j = 0; j < optionsArray.length(); j++) {
                                optionsList.add(optionsArray.getString(j));
                            }

                            QuizResponse.Question quiz = new QuizResponse.Question(questionText, optionsList, correctAnswer);
                            quizList.add(quiz);
                        }

                        Log.d("FETCH_SUCCESS", "Loaded " + quizList.size() + " questions");
                        Toast.makeText(getContext(), "Quiz loaded: " + quizList.size() + " questions", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        Log.e("FETCH_ERROR", "Error parsing JSON: " + e.getMessage());
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error parsing quiz", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("FETCH_NETWORK_ERROR", "Volley error: " + error.toString());
                    Toast.makeText(getContext(), "Network Error: " + error.toString(), Toast.LENGTH_LONG).show();
                }
        );

        // Set retry policy
        jsonObjectRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                30000, // 30 seconds timeout
                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(jsonObjectRequest);
    }
}
