package com.softthenext.gymmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class CustListActivity extends AppCompatActivity {
    /*private CustAttrendanceAdapter custAttrendanceAdapter;
     ArrayList<AttendanceModel> cArraylist;
    private RecyclerView crecyclerView;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initViews();


        //loadCustomerName();


    }

   /* public void initViews()
    {
        crecyclerView = findViewById(R.id.card_recycler_view);
        crecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        crecyclerView.setLayoutManager(layoutManager);
    }

    public void loadCustomerName()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface request = retrofit.create(ApiInterface.class);
       // Call<CustAttendanceResponse> call = request.getCustname();
        call.enqueue(new Callback<CustAttendanceResponse>() {
            @Override
            public void onResponse(Call<CustAttendanceResponse> call, Response<CustAttendanceResponse> response) {

                CustAttendanceResponse jsonResponse = response.body();
                cArraylist = new ArrayList<>(Arrays.asList(jsonResponse.getAttendanceModels()));
                custAttrendanceAdapter = new CustAttrendanceAdapter(cArraylist);
                crecyclerView.setAdapter(custAttrendanceAdapter);
            }

            @Override
            public void onFailure(Call<CustAttendanceResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void search(SearchView searchView)
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                custAttrendanceAdapter.getFilter().filter(newText);
                return true;
            }
        });

    }*/
}
