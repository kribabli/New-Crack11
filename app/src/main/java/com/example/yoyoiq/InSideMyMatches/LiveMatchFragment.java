package com.example.yoyoiq.InSideMyMatches;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.yoyoiq.Adapter.LiveMatchListAdapter;
import com.example.yoyoiq.MainActivity;
import com.example.yoyoiq.Model.TotalHomeData;
import com.example.yoyoiq.R;
import com.example.yoyoiq.Retrofit.ApiClient;
import com.example.yoyoiq.UpcommingReq.UpcommingResponse;
import com.example.yoyoiq.common.HelperData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveMatchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    LinearLayout linearLayout;
    TextView textView;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar live_progress_bar;
    ArrayList<TotalHomeData> list = new ArrayList<TotalHomeData>();

    public LiveMatchFragment() {
        // Required empty public constructor
    }

    public static LiveMatchFragment newInstance(String param1, String param2) {
        LiveMatchFragment fragment = new LiveMatchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LiveMatchListAdapter liveMatchListAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_live_match, container, false);
        linearLayout = root.findViewById(R.id.LinearLayout);
        textView = root.findViewById(R.id.upcomingMatch);
        recyclerView = root.findViewById(R.id.recyclerView);
        swipeRefreshLayout = root.findViewById(R.id.swiper);
        live_progress_bar=root.findViewById(R.id.live_progress_bar);

        getAllLiveMatches();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getAllLiveMatches();
            }
        });
        return root;
    }

    private void getAllLiveMatches() {
        list.clear();
        live_progress_bar.setVisibility(View.VISIBLE);
        Call<UpcommingResponse> call = ApiClient
                .getInstance()
                .getApi()
                .getLiveContestMatchesList(HelperData.UserId);
        call.enqueue(new Callback<UpcommingResponse>() {
            @Override
            public void onResponse(Call<UpcommingResponse> call, Response<UpcommingResponse> response) {
                UpcommingResponse status = response.body();
                if (response.isSuccessful()) {
                    String jsonArray = new Gson().toJson(status.getResponse().getItems());
                    JSONArray jsonArray1 = null;
                    try {
                        list.clear();
                        jsonArray1 = new JSONArray(jsonArray);
                        if(jsonArray1.length()>0){
                            live_progress_bar.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject jsonObject = jsonArray1.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String format_str = jsonObject.getString("format_str");
                            String match_id = jsonObject.getString("match_id");
                            String date_start = jsonObject.getString("date_start_ist");
                            String date_end = jsonObject.getString("date_end_ist");
                            String game_state_str = jsonObject.getString("game_state_str");

                            //for competition
                            JSONArray competition = jsonObject.getJSONArray("competition");
                            JSONObject jsonObject00 = competition.getJSONObject(0);
                            String abbr = jsonObject00.getString("abbr");

                            JSONArray teama1 = jsonObject.getJSONArray("teama");
                            JSONArray teamb1 = jsonObject.getJSONArray("teamb");

                            JSONObject jsonObject11 = teama1.getJSONObject(0);
                            JSONObject jsonObject22 = teamb1.getJSONObject(0);

                            String logo_url_a = jsonObject11.getString("logo_url");
                            String name_a = jsonObject11.getString("name");
                            String short_name_a = jsonObject11.getString("short_name");
//                            int teamIda = Integer.parseInt(jsonObject11.getString("team_id"));

                            String logo_url_b = jsonObject22.getString("logo_url");
                            String name_b = jsonObject22.getString("name");
                            String short_name_b = jsonObject22.getString("short_name");
//                            int teamIdb = Integer.parseInt(jsonObject22.getString("team_id"));
                            TotalHomeData totalHomeData = new TotalHomeData(game_state_str, format_str, title, match_id, logo_url_a, name_a, short_name_a, logo_url_b, name_b, short_name_b, date_start, date_end, abbr);
                            list.add(totalHomeData);
                        }
                    }
                        if (list.size() > 0) {
                            live_progress_bar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            liveMatchListAdapter = new LiveMatchListAdapter(getContext(), list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(liveMatchListAdapter);
                            liveMatchListAdapter.notifyDataSetChanged();
                        } else if (list.isEmpty()) {
                            swipeRefreshLayout.setRefreshing(false);
                            live_progress_bar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            linearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        live_progress_bar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        e.printStackTrace();
                    }
                } else {
                    live_progress_bar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<UpcommingResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getAllLiveMatches();
        }
    }
}