package com.ali.rnp.khodemon.Api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.DataModel.ListLayout;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.DataModel.Tags;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.SingleCheckItemsExp;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ApiService {

    private static final String TAG = "ApiService";

    private final static String API_REGISTER_USER = "http://khodemon.ir/registerUser.php";
    private final static String API_LOGIN_USER = "http://khodemon.ir/loginUser.php";
    private final static String API_GET_HOME_ITEMS = "http://khodemon.ir/getHomeItems.php";
    private final static String API_GET_GROUP_ITEMS = "http://khodemon.ir/getGroupItems.php";
    private final static String API_GET_HOME_LIST_ITEMS = "http://khodemon.ir/getHomeItemsList.php";
    private final static String API_ADD_LOCATION_PEOPLE = "http://khodemon.ir/addLocationPeople.php";
    private final static String API_GET_PROVINCE = "http://khodemon.ir/json/Province.json";
    private final static String API_GET_TAGS = "http://khodemon.ir/json/Tags.json";

    private final static String API_UPLOAD_PHOTOS = "http://khodemon.ir/upload_images.php";
    private final static String API_ADD_PICTURE = "http://khodemon.ir/addPictures.php";
    private final static String API_GET_PICTURE = "http://khodemon.ir/getPictures.php";
    private final static String API_GET_DETAIL = "http://khodemon.ir/getDetail.php";
    private final static String API_GET_PERSONNEL = "http://khodemon.ir/getPersonnel.php";

    private final static String API_TEST = "http://amirabbaszareii.ir/php/phpslider.json";


    public static final int STATUS_REGISTER_ERROR = 616;
    public static final int STATUS_Login_ERROR = 717;


    public static final String GROUP_NAME_LOCATION = "LOCATION";
    public static final String GROUP_NAME_PEOPLE = "PEOPLE";

    public static final int LOCATION_GROUP_KEY = 1;
    public static final int PEOPLE_GROUP_KEY = 2;

    private JSONObject jsonObjectPhoto;


    private int retryTime = 10000;
    private Context context;

    List<LocationPeople> locationPeoplePerItem;
    List<String> imageUrlList;

    public ApiService(Context context) {
        this.context = context;
        imageUrlList = new ArrayList<>();
    }

    public void registerUser(JSONObject jsonObject, final OnRegisterCompleted onRegisterCompleted) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_REGISTER_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                parseJsonRegisterUser(response, onRegisterCompleted);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRegisterCompleted.onRegisterStatusReceived(STATUS_REGISTER_ERROR);

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }

    public void loginUser(JSONObject jsonObject, final OnLoginCompleted onLoginCompleted) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_LOGIN_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonLoginUser(response, onLoginCompleted);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoginCompleted.onLoginStatusReceived(false, 0, null, error.toString());
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    /*
        public void getHomeItems(final OnHomeItemReceived onHomeItemReceived) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_HOME_ITEMS, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    parseJsonHomeItems(response, onHomeItemReceived);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            onHomeItemReceived.onItemReceived(null, error);
                        }
                    }
            );

            request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(request);
        }
    */
    public void getGroupItems(JSONObject jsonObjectGroup, final int groupKey, final OnGroupItemReceived onGroupItemReceived) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_GROUP_ITEMS, jsonObjectGroup, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonGroupItems(response, groupKey, onGroupItemReceived);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onGroupItemReceived.onItemGroupReceived(null, error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }


    public void getPersonnel(int LOCATION_ID , OnPersonnelReceived onPersonnelReceived) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_PERSONNEL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonPersonnel(response, onPersonnelReceived);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onPersonnelReceived.onItemReceived(null, null, error.toString());
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }



    public void getHomeRecyclerListItems(final OnHomeListItemReceived onHomeListItemReceived) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_HOME_LIST_ITEMS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonHomeRecyclerListItems(response, onHomeListItemReceived);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onHomeListItemReceived.onItemReceived(null, null, "VolleyError | " + error.toString());
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getProvince(OnProvinceReceived onProvinceReceived) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API_GET_PROVINCE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJsonProvince(response, onProvinceReceived);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onProvinceReceived.onReceived(null, null, error);

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Volley.newRequestQueue(context).add(request);
        requestQueue.add(request);
    }

    public void getTags(OnTagsReceived onTagsReceived) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API_GET_TAGS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJsonTags(response, onTagsReceived);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onTagsReceived.onReceived(null, null, error);

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Volley.newRequestQueue(context).add(request);
        requestQueue.add(request);
    }

    public void uploadImage(Bitmap bitmap, String ImageName, int pic_id, String groupName, final int currentPhoto, int allPhoto, OnUploadedPhoto onUploadedPhoto) {


        try {
            jsonObjectPhoto = new JSONObject();
            //  String imgName = String.valueOf(Calendar.getInstance().getTimeInMillis());

            //jsonObject.put("id",currentPhoto);
            jsonObjectPhoto.put("name", ImageName);
            jsonObjectPhoto.put("group", groupName);
            jsonObjectPhoto.put("pic_id", pic_id);
            jsonObjectPhoto.put("image", bitmapToString(bitmap));

        } catch (JSONException e) {
            Log.e("JSONObject Here", e.toString());
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_UPLOAD_PHOTOS, jsonObjectPhoto,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Log.i(TAG, "onResponse: " + jsonObject.toString());
                        try {
                            JSONObject jsonObjectPhoto = new JSONObject(jsonObject.toString());
                            if (jsonObjectPhoto.getInt("success") == 1) {
                                /*
                                int success = jsonObjectPhoto.getInt("success");
                                String imageUrl = jsonObjectPhoto.getString("url");
                                int pic_id_get = jsonObjectPhoto.getInt("pic_id");

                                int width = jsonObjectPhoto.getInt("width");
                                int height = jsonObjectPhoto.getInt("height");
                                String thumb_150 = jsonObjectPhoto.getString("thumb_150");
                                String thumb_1000 = jsonObjectPhoto.getString("thumb_1000");
*/
                                PictureUpload pictureUploaded = new PictureUpload();
                                pictureUploaded.setPic_address(jsonObjectPhoto.getString("url"));
                                pictureUploaded.setPic_id(jsonObjectPhoto.getInt("pic_id"));
                                pictureUploaded.setWidth(jsonObjectPhoto.getInt("width"));
                                pictureUploaded.setHeight(jsonObjectPhoto.getInt("height"));
                                pictureUploaded.setThumb_150(jsonObjectPhoto.getString("thumb_150"));
                                pictureUploaded.setThumb_1000(jsonObjectPhoto.getString("thumb_1000"));
                                Log.i(TAG, "onResponse: " + jsonObject.toString());
                                Log.i(TAG, "onResponse: " + pictureUploaded.toString());
                                onUploadedPhoto.OnUploadPhoto(pictureUploaded, currentPhoto, null);
                            } else {
                                Log.i(TAG, "onResponse: ERROR - Succees = 0");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, volleyError.toString());
                onUploadedPhoto.OnUploadPhoto(null, -1, volleyError);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(120000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(jsonObjectRequest);

    }

    public void addLocation(JSONObject jsonObject, OnAddLocationPeople onAddLocationPeople) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_ADD_LOCATION_PEOPLE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObjectResponse = new JSONObject(response.toString());
                    JSONObject jsonObjectResult = jsonObjectResponse.getJSONObject("result");
                    // String res = jsonObjectResponse.getString("result");
                    jsonObjectResult.getInt("success");
                    if (jsonObjectResult.getInt("success") == 1) {
                        onAddLocationPeople.OnAdded(jsonObjectResult.getInt("last_id"), null);
                    } else {
                        String msg = jsonObjectResult.getString("message");
                        onAddLocationPeople.OnAdded(-1, "Error From Server " + msg);
                        Log.i(TAG, "onResponse: " + msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onAddLocationPeople.OnAdded(-1, error.toString());
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void addPicture(JSONObject jsonObject, OnAddPictures onAddPictures) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_ADD_PICTURE, jsonObject, response -> {

            try {
                JSONObject jsonObjectResponse = new JSONObject(response.toString());
                String res = jsonObjectResponse.getString("result");
                onAddPictures.OnAddPicture(res, null);
            } catch (JSONException e) {
                e.printStackTrace();


            }

        }, error -> onAddPictures.OnAddPicture(null, error));

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getPicture(int Post_id, OnGetPictures onGetPictures) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProvidersApp.KEY_POST_ID, Post_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_PICTURE, jsonObject, response -> {

            try {
                JSONObject jsonObjectResponse = new JSONObject(response.toString());
                //String res = jsonObjectResponse.getString("result");
                JSONArray jsonArrayRes = jsonObjectResponse.getJSONArray("result");

                ArrayList<String> imgAddressList = new ArrayList<>();
                ArrayList<PictureUpload> pictureUploadsList = new ArrayList<>();

                for (int i = 0; i < jsonArrayRes.length(); i++) {
                    JSONObject jsObject = jsonArrayRes.getJSONObject(i);
                    //imgAddressList.add(jsObject.getString("pic_address"));

                    PictureUpload picUp = new PictureUpload();
                    //picUp.setIs_original(jsObject.getBoolean("is_original"));
                    picUp.setPic_address(jsObject.getString("pic_address"));
                    picUp.setWidth(jsObject.getInt("width"));
                    picUp.setHeight(jsObject.getInt("height"));
                    picUp.setThumb_150(jsObject.getString("thumb_150"));
                    picUp.setThumb_1000(jsObject.getString("thumb_1000"));

                    pictureUploadsList.add(picUp);
                }
                onGetPictures.OnGetPicture(pictureUploadsList, null);

            } catch (JSONException e) {
                e.printStackTrace();


            }

        }, error -> onGetPictures.OnGetPicture(null, error));

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getDetail(int Post_id, OnGetDetails onGetDetails) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProvidersApp.KEY_POST_ID, Post_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_DETAIL, jsonObject, response -> {

            parseGetDetail(response, onGetDetails);


        }, error -> onGetDetails.OnGetDetail(null, error));

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }


    private void parseJsonGroupItems(JSONObject response, int groupKey, OnGroupItemReceived onGroupItemReceived) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArrayResult = jsonObject.getJSONArray("result");

            ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();

            for (int i = 0; i < jsonArrayResult.length(); i++) {

                JSONObject jsonObjectLocPeo = jsonArrayResult.getJSONObject(i);

                LocationPeople locationPeople = new LocationPeople();

                if (groupKey == LOCATION_GROUP_KEY) {
                    locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                    locationPeople.setGroup(jsonObjectLocPeo.getString("group_name"));
                    locationPeople.setName(jsonObjectLocPeo.getString("name"));
                    locationPeople.setTag(jsonObjectLocPeo.getString("tagLocPeo"));
                    locationPeople.setOwnerSeller(jsonObjectLocPeo.getString("owner_seller"));
                    locationPeople.setAddress(jsonObjectLocPeo.getString("address"));
                    locationPeople.setOriginalPic(jsonObjectLocPeo.getString("original_pic"));
                } else if (groupKey == PEOPLE_GROUP_KEY) {
                    locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                    locationPeople.setGroup(jsonObjectLocPeo.getString("group_name"));
                    locationPeople.setName(jsonObjectLocPeo.getString("name"));
                    locationPeople.setTag(jsonObjectLocPeo.getString("tagLocPeo"));
                    locationPeople.setCity(jsonObjectLocPeo.getString("city"));
                    locationPeople.setWork_experience(jsonObjectLocPeo.getInt("work_experience"));
                    locationPeople.setExperts(jsonObjectLocPeo.getString("experts"));
                    locationPeople.setOriginalPic(jsonObjectLocPeo.getString("original_pic"));
                }


                locationPeopleList.add(locationPeople);
            }
            onGroupItemReceived.onItemGroupReceived(locationPeopleList, null);

        } catch (JSONException e) {
            Log.i(TAG, "parseJsonHomeItems: " + e);

        }

    }

    private void parseJsonPersonnel(JSONObject response, OnPersonnelReceived onPersonnelReceived) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArrayResult = jsonObject.getJSONArray("result");
            JSONObject jsonObjectRes = jsonArrayResult.getJSONObject(0);
            boolean isSuccess = Boolean.parseBoolean(jsonObjectRes.getString("success"));
            if (isSuccess){
                JSONArray itemsArray = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();
                ArrayList<PictureUpload> pictureUploadList = new ArrayList<>();

                for (int i = 0; i < itemsArray.length(); i++) {

                    JSONObject jsonObjectLocPeo = itemsArray.getJSONObject(i);

                    LocationPeople locationPeople = new LocationPeople();
                    PictureUpload pictureUpload = new PictureUpload();

                    locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                    locationPeople.setName(jsonObjectLocPeo.getString("personnelName"));
                    locationPeople.setTag(jsonObjectLocPeo.getString("TagPeople"));
                    locationPeople.setOriginalPic(jsonObjectLocPeo.getString("image"));

                    pictureUpload.setPic_address(jsonObjectLocPeo.getString("image"));
                    pictureUpload.setWidth(jsonObjectLocPeo.getInt("width"));
                    pictureUpload.setHeight(jsonObjectLocPeo.getInt("height"));
                    pictureUpload.setThumb_150(jsonObjectLocPeo.getString("thumb_150"));
                    pictureUpload.setThumb_1000(jsonObjectLocPeo.getString("thumb_1000"));

                    locationPeopleList.add(locationPeople);
                    pictureUploadList.add(pictureUpload);
                }
                onPersonnelReceived.onItemReceived(locationPeopleList,pictureUploadList, null);
            }else {
                String  msg = jsonObjectRes.getString("message");
                onPersonnelReceived.onItemReceived(null, null, msg);
            }



        } catch (JSONException e) {
            onPersonnelReceived.onItemReceived(null, null, e.toString());

        }

    }

    private void parseJsonHomeRecyclerListItems(JSONObject response, OnHomeListItemReceived onHomeListItemReceived) {

        try {

            JSONObject jsonObject = new JSONObject(response.toString());

            JSONArray jsonArrayResult = jsonObject.getJSONArray("result");

            List<ListLayout> locationPeopleListLayout = new ArrayList<>();
            for (int i = 0; i < jsonArrayResult.length(); i++) {

                JSONObject jsonObjectGp = jsonArrayResult.getJSONObject(i);
                JSONArray jsonArrayData = jsonObjectGp.getJSONArray("data");


                locationPeoplePerItem = new ArrayList<>();
                for (int j = 0; j < jsonArrayData.length(); j++) {

                    JSONObject jsonObjectGroup = jsonArrayData.getJSONObject(j);


                    LocationPeople locationPeople = new LocationPeople();

                    locationPeople.setId(jsonObjectGroup.getInt("ID"));
                    locationPeople.setGroup(jsonObjectGroup.getString("group_name"));
                    locationPeople.setName(jsonObjectGroup.getString("name"));
                    locationPeople.setTag(jsonObjectGroup.getString("tag"));
                    locationPeople.setOriginalPic(jsonObjectGroup.getString("original_pic"));

                    locationPeoplePerItem.add(locationPeople);

                }
                ListLayout listLayout = new ListLayout();

                listLayout.setId(i);
                listLayout.setTitle(jsonObjectGp.getString("title"));
                listLayout.setGroup(jsonObjectGp.getString("group"));
                listLayout.setLocationPeopleList(locationPeoplePerItem);

                locationPeopleListLayout.add(listLayout);

            }


            onHomeListItemReceived.onItemReceived(locationPeopleListLayout, locationPeoplePerItem, null);

        } catch (JSONException e) {
            onHomeListItemReceived.onItemReceived(null, null, "JSONException | " + e.toString());

        }

    }

    private void parseJsonProvince(JSONArray response, OnProvinceReceived onProvinceReceived) {

        List<City> cities = new ArrayList<>();
        List<SingleCheckItemsExp> makeSingleCheckParent = new ArrayList<>();


        for (int i = 0; i < response.length(); i++) {

            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String provinceName = jsonObject.getString("name");
                JSONArray jsonArrayCities = jsonObject.getJSONArray("Cities");

                List<ChildExp> makeChild = new ArrayList<>();
                for (int j = 0; j < jsonArrayCities.length(); j++) {
                    JSONObject jsonObjectCity = jsonArrayCities.getJSONObject(j);
                    String cityName = jsonObjectCity.getString("name");

                    City city = new City();
                    city.setCityName(cityName);
                    city.setProvince(provinceName);
                    cities.add(city);

                    ChildExp childExp = new ChildExp();
                    childExp.setData(cityName, true);
                    makeChild.add(childExp);
                }


                SingleCheckItemsExp makeSingleCheckChild = new SingleCheckItemsExp(provinceName, makeChild, R.drawable.ic_placeholder);
                makeSingleCheckParent.add(makeSingleCheckChild);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        onProvinceReceived.onReceived(makeSingleCheckParent, cities, null);

    }

    private void parseJsonTags(JSONArray response, OnTagsReceived onTagsReceived) {

        List<Tags> tagsList = new ArrayList<>();
        List<SingleCheckItemsExp> makeSingleCheckParent = new ArrayList<>();


        for (int i = 0; i < response.length(); i++) {

            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String parentName = jsonObject.getString("name");
                String imgUrl = jsonObject.getString("img");
                JSONArray jsonArrayChild = jsonObject.getJSONArray("child");

                List<ChildExp> makeChild = new ArrayList<>();
                for (int j = 0; j < jsonArrayChild.length(); j++) {
                    JSONObject jsonObjectChild = jsonArrayChild.getJSONObject(j);
                    String childName = jsonObjectChild.getString("name");

                    Tags tags = new Tags();
                    tags.setParentName(parentName);
                    tags.setChildName(childName);
                    tags.setImgUrl(imgUrl);

                    tagsList.add(tags);


                    ChildExp childExp = new ChildExp();
                    childExp.setData(childName, true);
                    makeChild.add(childExp);
                }


                SingleCheckItemsExp makeSingleCheckChild = new SingleCheckItemsExp(parentName, makeChild, R.drawable.ic_tyre_expert);
                makeSingleCheckParent.add(makeSingleCheckChild);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        onTagsReceived.onReceived(makeSingleCheckParent, tagsList, null);

    }

    private void parseJsonRegisterUser(JSONObject response, OnRegisterCompleted onRegisterCompleted) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            String status = jsonObject.getString("result");
            onRegisterCompleted.onRegisterStatusReceived(Integer.parseInt(status));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJsonLoginUser(JSONObject response, OnLoginCompleted onLoginCompleted) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject res = result.getJSONObject(0);
            boolean isSuccess = Boolean.parseBoolean(res.getString("success"));
            int code = res.getInt("code");
            String username = res.getString("username");

            onLoginCompleted.onLoginStatusReceived(isSuccess, code, username, null);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseGetDetail(JSONObject response, OnGetDetails onGetDetails) {
        try {
            JSONObject jsonObjectResponse = new JSONObject(response.toString());
            JSONArray jsonArrayRes = jsonObjectResponse.getJSONArray("result");


            JSONObject jsObject = jsonArrayRes.getJSONObject(0);


            LocationPeople locPeo = new LocationPeople();

            locPeo.setTimeReg(jsObject.getString("reg_date"));
            locPeo.setOwnerSeller(jsObject.getString("owner_seller"));


            onGetDetails.OnGetDetail(locPeo, null);

        } catch (JSONException e) {
            e.printStackTrace();


        }
    }


    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

    }


    public interface OnRegisterCompleted {
        void onRegisterStatusReceived(int status);
    }

    public interface OnLoginCompleted {
        void onLoginStatusReceived(boolean isSuccess, int code, String username, String error);
    }

    public interface OnHomeItemReceived {
        void onItemReceived(List<LocationPeople> locationPeopleList, VolleyError error);
    }

    public interface OnHomeListItemReceived {
        void onItemReceived(List<ListLayout> listLayouts, List<LocationPeople> locationPeopleList, String error);
    }

    public interface OnGroupItemReceived {
        void onItemGroupReceived(ArrayList<LocationPeople> locationPeopleList, VolleyError error);
    }

    public interface OnPersonnelReceived {
        void onItemReceived(ArrayList<LocationPeople> locationPeopleList, ArrayList<PictureUpload> pictureUploadList, String error);
    }

    public interface OnProvinceReceived {
        void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent, List<City> cities, VolleyError error);
    }

    public interface OnTagsReceived {
        void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent, List<Tags> tags, VolleyError error);
    }

    public interface OnUploadedPhoto {
        void OnUploadPhoto(PictureUpload pictureUploaded, int currentPhotoNum, VolleyError error);
    }

    public interface OnAddLocationPeople {
        void OnAdded(int last_id, String error);
    }

    public interface OnAddPictures {
        void OnAddPicture(String result, VolleyError error);
    }

    public interface OnGetPictures {
        void OnGetPicture(ArrayList<PictureUpload> pictureUploadList, VolleyError error);
    }

    public interface OnGetDetails {
        void OnGetDetail(LocationPeople locationPeople, VolleyError error);
    }


}