package com.codeasylums.stockmarket;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

  private GoogleApiClient client;
  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager shareDataLayoutManager;
  private RecyclerView.Adapter shareDataAdapter;


  final List<SharesData> shareDataList = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rview);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    addInitialDataToSharedList();

    shareDataLayoutManager=new LinearLayoutManager(this);
    recyclerView.setLayoutManager(shareDataLayoutManager);
    //myadapter is the adapter class
    shareDataAdapter=new myadapter(shareDataList);
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
      return true;
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

    //initializing the fragment object which is selected
    switch (itemId) {
      case R.id.stockmarket:
        fragment = new AboutStockMarketFragment();
        break;
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

    //replacing the fragment
    if (fragment != null) {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.content_frame, fragment);
      ft.commit();
    }
        /*LayoutInflater inflater = getLayoutInflater();
        RelativeLayout container = (RelativeLayout) findViewById(R.id.content_frame);
        inflater.inflate(R.layout.activity_main, container);*/

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
        if (receivedGooglData[0] == true && receivedMsftata[0] == true
            && receivedApplData[0] == true && receivedFbData[0] == true) {
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