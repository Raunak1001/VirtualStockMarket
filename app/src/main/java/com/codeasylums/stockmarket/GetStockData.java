package com.codeasylums.stockmarket;

import android.util.Log;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import java.text.DecimalFormat;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raunak on 21/5/17.
 */

public class GetStockData {


  static               String tag_json_obj = "json_obj_req";

  public void getStockData(String companyName,ShareRateCallBack shareRateCallBack) {
    String baseUrl = "http://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&interval=1min&apikey=N52O";
    baseUrl = baseUrl + "&symbol=" + companyName;

    JsonRequest jsonRequest = new JsonObjectRequest(Method.GET, baseUrl, null,
        new Response.Listener<JSONObject>() {

          @Override
          public void onResponse(JSONObject response) {

            DateTimeZone zone = DateTimeZone.forID("US/Eastern");
            DateTime dateTime = new DateTime(zone);
            int date = dateTime.getDayOfMonth();
            int hour = dateTime.getHourOfDay();
            int minute = dateTime.getMinuteOfHour();
            int weekDay = dateTime.getDayOfWeek();
            if (weekDay == 6) {
              hour = 16;
              minute = 0;
              dateTime = dateTime.minusDays(1);
              date = dateTime.getDayOfMonth();


            } else if (weekDay == 7) {
              hour = 16;
              minute = 0;
              dateTime = dateTime.minusDays(2);
              date = dateTime.getDayOfMonth();
            } else if (hour > 16) {
              hour = 16;
              minute = 0;
            } else if (hour < 9) {
              hour = 16;
              minute = 0;
              dateTime = dateTime.minusDays(1);
              date = dateTime.getDayOfMonth();
            }
            int year = dateTime.getYear();
            int month = dateTime.getMonthOfYear();

            DecimalFormat formatter = new DecimalFormat("00");
            String dateSting =
                year + "-" + formatter.format(month) + "-" + formatter.format(date) + " "
                    + formatter.format(hour) + ":" + formatter.format(minute) + ":00";

            Log.d("OUTPUT", dateSting);

            try {
              JSONObject timeSeriesObject = response.getJSONObject("Time Series (1min)");
              Log.d("MAIN", response.getJSONObject("Time Series (1min)").toString());
              String shareRate = timeSeriesObject.getJSONObject(dateSting).getString("4. close");
              Log.d("SHARE_RATE", shareRate.toString());
            } catch (JSONException e) {
              e.printStackTrace();
            }

          }
        }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {

      }
    });

    AppController.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);


  }


}
