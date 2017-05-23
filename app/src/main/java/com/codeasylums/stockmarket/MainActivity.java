package com.codeasylums.stockmarket;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private RecyclerView               recyclerView;
  private RecyclerView.LayoutManager shareDataLayoutManager;
  private myadapter                  shareDataAdapter;
  private SwipeRefreshLayout         swipeRefreshLayout;
  AlertDialog alertDialog;
  SharedPreferences sharedPreferences;

  final List<SharesData> shareDataList = new ArrayList<>();
  FragmentTransaction ft;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
    recyclerView = (RecyclerView) findViewById(R.id.rview);
    alertDialog =new AlertDialog.Builder(this).create();
    alertDialog.setCancelable(false);
sharedPreferences = getSharedPreferences("Wallet",MODE_PRIVATE);
    String amount =sharedPreferences.getString("MONEY","0");
    if(Double.parseDouble(amount)==0){
      Editor editor= sharedPreferences.edit();
      editor.putString("MONEY","25000");
      editor.commit();
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    swipeRefreshLayout.setRefreshing(true);
    swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getShareData();
      }
    });

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    addInitialDataToSharedList();

    shareDataLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(shareDataLayoutManager);
    //myadapter is the adapter class
    shareDataAdapter = new myadapter(shareDataList, this);
    recyclerView.setAdapter(shareDataAdapter);

    getShareData();

  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);


    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      String amount=sharedPreferences.getString("MONEY","0");
        alertDialog.setTitle("Your Balance");
        alertDialog.setMessage(amount);
        alertDialog.setCancelable(true);
        alertDialog.show();
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            alertDialog.hide();
          }
        });

        Log.d("WALLET", "wallet");
      }


    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    displaySelectedScreen(item.getItemId());
    return true;
  }

  private void displaySelectedScreen(int itemId) {

    //creating fragment object
    Fragment fragment = null;

    if (itemId == R.id.stockmarket) {
      setTitle("Stock Prices");
      getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
      recyclerView.setVisibility(View.VISIBLE);
    } else {
      switch (itemId) {

        case R.id.mystocks:
          fragment = new MyStocksFragment();
          break;
        case R.id.mytransactions:
          fragment = new MyTransactionsFragment();
          break;
        case R.id.aboutapp:
          fragment = new AboutAppFragment();
          break;
        case R.id.aboutus:
          fragment = new AboutUsFragment();
          break;
      }

      if (fragment != null) {
        ft = getSupportFragmentManager().beginTransaction();
        recyclerView.setVisibility(View.INVISIBLE);
       /* if(getSupportFragmentManager().getFragments() !=null && getSupportFragmentManager().getFragments().size()!=0){
          getSupportFragmentManager().getFragments().clear();
        }*/
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

      }
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
  }


  public void getShareData() {
    final boolean[] receivedGooglData = {false};
    final boolean[] receivedMsftata = {false};
    final boolean[] receivedApplData = {false};
    final boolean[] receivedFbData = {false};

    new GetStockData().getStockData("GOOGL", new ShareRateCallBack() {
      @Override
      public void onSuccess(String shareRate) {
        shareDataList.get(0).setShareRate(shareRate);
        receivedGooglData[0] = true;
        if (receivedMsftata[0] && receivedApplData[0] && receivedFbData[0]) {
          shareDataAdapter.update(shareDataList);
          swipeRefreshLayout.setRefreshing(false);
          Log.d("GOTALLDATA", "AYA");
        }

      }
    });

    new GetStockData().getStockData("MSFT", new ShareRateCallBack() {
      @Override
      public void onSuccess(String shareRate) {
        shareDataList.get(1).setShareRate(shareRate);
        receivedMsftata[0] = true;
        if (receivedGooglData[0]
            && receivedMsftata[0] && receivedApplData[0] && receivedFbData[0]) {
          shareDataAdapter.update(shareDataList);
          swipeRefreshLayout.setRefreshing(false);

          Log.d("GOTALLDATA", "AYA");
        }
      }
    });
    new GetStockData().getStockData("AAPL", new ShareRateCallBack() {
      @Override
      public void onSuccess(String shareRate) {
        shareDataList.get(2).setShareRate(shareRate);
        receivedApplData[0] = true;
        if (receivedGooglData[0]
            && receivedMsftata[0] && receivedApplData[0] && receivedFbData[0]) {
          shareDataAdapter.update(shareDataList);
          swipeRefreshLayout.setRefreshing(false);

          Log.d("GOTALLDATA", "AYA");
        }
      }
    });
    new GetStockData().getStockData("FB", new ShareRateCallBack() {
      @Override
      public void onSuccess(String shareRate) {
        shareDataList.get(3).setShareRate(shareRate);
        receivedFbData[0] = true;
        if (receivedGooglData[0]
            && receivedMsftata[0] && receivedApplData[0] && receivedFbData[0]) {
          shareDataAdapter.update(shareDataList);
          swipeRefreshLayout.setRefreshing(false);

          Log.d("GOTALLDATA", "AYA");
        }
      }
    });


  }


  public void addInitialDataToSharedList() {

    SharesData sharesData = new SharesData();
    sharesData.setShareRate("0");
    sharesData.setShareName("GOOGL");
    shareDataList.add(0, sharesData);

    sharesData = new SharesData();
    sharesData.setShareRate("0");
    sharesData.setShareName("MSFT");
    shareDataList.add(1, sharesData);

    sharesData = new SharesData();
    sharesData.setShareRate("0");
    sharesData.setShareName("APPL");
    shareDataList.add(2, sharesData);

    sharesData = new SharesData();
    sharesData.setShareRate("0");
    sharesData.setShareName("FB");
    shareDataList.add(3, sharesData);


  }


}