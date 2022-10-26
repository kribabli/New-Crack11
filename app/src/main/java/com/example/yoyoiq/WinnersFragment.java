package com.example.yoyoiq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yoyoiq.Model.WinnersListChildItem;
import com.example.yoyoiq.Model.WinnersListPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WinnersFragment extends Fragment {
    RecyclerView recyclerView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String url = "http://adminapp.tech/crack11/ItsMe/all_apis.php?func=winner_third_slide";
    String logo_url_a, name_a, short_name_a;
    String logo_url_b, name_b, short_name_b;
    String prize_pool;
    String userid;
    String rank;
    String money;
    String name;
    String image;
    ArrayList<WinnersListPOJO> list = new ArrayList<>();
    List<WinnersListChildItem> ChildItemList = new ArrayList<>();

    public WinnersFragment() {
    }

    public static WinnersFragment newInstance(String param1, String param2) {
        WinnersFragment fragment = new WinnersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getAllWinners();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    MegaContestAdapter megaContestAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_winners, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        return root;
    }


    public void getAllWinners() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray1 = new JSONArray();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    jsonArray1 = jsonObject.getJSONArray("match_response");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                        String match_id = jsonObject1.getString("match_id");
                        String title = jsonObject1.getString("title");
                        String format_str = jsonObject1.getString("format_str");
                        String date_start_ist = jsonObject1.getString("date_start_ist");

                        try {
                            //For TeamA
                            String jsonArrayA = jsonObject1.getString("teama");
                            JSONArray jsonArray = new JSONArray(jsonArrayA);
                            for (int m = 0; m < jsonArray.length(); m++) {
                                JSONObject jsonObject11 = jsonArray.getJSONObject(m);
                                logo_url_a = jsonObject11.getString("logo_url");
                                name_a = jsonObject11.getString("name");
                                short_name_a = jsonObject11.getString("short_name");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            //For TeamB
                            String jsonArrayB = jsonObject1.getString("teamb");
                            JSONArray jsonArray = new JSONArray(jsonArrayB);
                            for (int n = 0; n < jsonArray.length(); n++) {
                                JSONObject jsonObject22 = jsonArray.getJSONObject(n);
                                logo_url_b = jsonObject22.getString("logo_url");
                                name_b = jsonObject22.getString("name");
                                short_name_b = jsonObject22.getString("short_name");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //For contest_data
                        JSONArray jsonArrayContestData = jsonObject1.getJSONArray("contest_data");
                        for (int j = 0; j < jsonArrayContestData.length(); j++) {
                            JSONObject jsonObject2 = jsonArrayContestData.getJSONObject(j);
                            prize_pool = jsonObject2.getString("prize_pool");
                        }

                        //For winners_reponse
                        JSONArray jsonArrayWinnerResponse = jsonObject1.getJSONArray("winners_reponse");
                        for (int k = 0; k < jsonArrayWinnerResponse.length(); k++) {
                            JSONObject jsonObject2 = jsonArrayWinnerResponse.getJSONObject(k);
                            userid = jsonObject2.getString("userid");
                            rank = jsonObject2.getString("rank");
                            money = jsonObject2.getString("money");
                            name = jsonObject2.getString("name");
                            image = jsonObject2.getString("image");
                        }
                        WinnersListChildItem winnersListChildItem = new WinnersListChildItem(userid, rank, money, name, image);
                        ChildItemList.add(winnersListChildItem);

                        WinnersListPOJO winnersListPOJO = new WinnersListPOJO(match_id, title, format_str, date_start_ist, logo_url_a, name_a, short_name_a, logo_url_b, name_b, short_name_b, prize_pool, ChildItemList);
                        list.add(winnersListPOJO);
                    }

                    megaContestAdapter = new MegaContestAdapter(getContext(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(megaContestAdapter);
                    megaContestAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(request);
    }

    //MegaContest Adapter..
    public class MegaContestAdapter extends RecyclerView.Adapter<MegaContestAdapter.MyViewHolder> {
        Context context;
        ArrayList<WinnersListPOJO> list;

        public MegaContestAdapter(Context context, ArrayList<WinnersListPOJO> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MegaContestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_winners_cardview, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MegaContestAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

            //for date time.
            String dateTime = list.get(position).getDate_start_ist();
            String actualDate = dateTime.split("\\ ")[0].split("\\ ")[0];

            SimpleDateFormat month_date = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(actualDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String month_name = month_date.format(date);

            if (list.size() > 0) {
                holder.matchA.setText(list.get(position).getShort_name_a());
                holder.matchB.setText(list.get(position).getShort_name_b());
                holder.matchName.setText(list.get(position).getTitle());
                holder.matchDate.setText(month_name);
                Glide.with(context)
                        .load(list.get(position).getLogo_url_a())
                        .into(holder.teamAImg);
                Glide.with(context)
                        .load(list.get(position).getLogo_url_b())
                        .into(holder.teamBImg);
            }

            MegaContestAdapter1 megaContestAdapter1;
            megaContestAdapter1 = new MegaContestAdapter1(context, list.get(position).ChildItemList);
            holder.userRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.userRecyclerView.setAdapter(megaContestAdapter1);
            megaContestAdapter1.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView teamAImg, teamBImg, userImg;
            TextView matchA, matchB, matchName, userName, rank, wonPrize, matchDate;
            RecyclerView userRecyclerView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                teamAImg = itemView.findViewById(R.id.teamAImg);
                teamBImg = itemView.findViewById(R.id.teamBImg);
                matchA = itemView.findViewById(R.id.match1);
                matchB = itemView.findViewById(R.id.match2);
                matchName = itemView.findViewById(R.id.matchName);
                matchDate = itemView.findViewById(R.id.matchDate);

                userImg = itemView.findViewById(R.id.userImg);
                userName = itemView.findViewById(R.id.userName);
                rank = itemView.findViewById(R.id.rank);
                wonPrize = itemView.findViewById(R.id.wonPrize);
                userRecyclerView = itemView.findViewById(R.id.userRecyclerView);
            }
        }
    }

    //MegaContest1 Adapter..
    public class MegaContestAdapter1 extends RecyclerView.Adapter<MegaContestAdapter1.MyViewHolder> {
        Context context;
        List<WinnersListChildItem> list1;

        public MegaContestAdapter1(Context context, List<WinnersListChildItem> list1) {
            this.context = context;
            this.list1 = list1;
        }

        @NonNull
        @Override
        public MegaContestAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_ranking_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MegaContestAdapter1.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (list1.size() > 0) {
                Glide.with(context)
                        .load(list1.get(position).getImage())
                        .placeholder(R.drawable.ic_profile_pic)
                        .into(holder.userImg);
                holder.userName.setText(list1.get(position).getName());
                holder.rank.setText("Rank #" + list1.get(position).getRank());
                holder.wonPrize.setText(list1.get(position).getMoney());
            }
        }

        @Override
        public int getItemCount() {
            return list1.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView userImg;
            TextView userName, rank, wonPrize;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                userImg = itemView.findViewById(R.id.userImg);
                userName = itemView.findViewById(R.id.userName);
                rank = itemView.findViewById(R.id.rank);
                wonPrize = itemView.findViewById(R.id.wonPrize);
            }
        }
    }
}