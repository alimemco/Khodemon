package com.ali.rnp.khodemon.Api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.ali.rnp.khodemon.DataModel.Category;
import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.DataModel.Filter;
import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.DataModel.ListLayout;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.SingleCheckItemsExp;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Search.GroupModel;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ApiService {

    public static final int STATUS_REGISTER_ERROR = 616;
    public static final int STATUS_Login_ERROR = 717;/*
    public static final String GROUP_NAME_LOCATION = "LOCATION";
    public static final String GROUP_NAME_PEOPLE = "PEOPLE";*/
    public static final int LOCATION_GROUP_KEY = 1;
    public static final int PEOPLE_GROUP_KEY = 2;
    private final static String SITE = "http://khodemon.ir/";
    private final static String FOLDER = "androidConnector/";
    private final static String JSON_FOLDER = "json/";
    private final static String API_REGISTER_USER = SITE + FOLDER + "registerUser.php";
    private final static String API_LOGIN_USER = SITE + FOLDER + "loginUser.php";
    private final static String API_GET_HOME_ITEMS = SITE + FOLDER + "getHomeItems.php";
    private final static String API_GET_GROUP_ITEMS = SITE + FOLDER + "getGroupItems.php";
    private final static String API_GET_HOME_LIST_ITEMS = SITE + FOLDER + "getHomeItemsList.php";
    private final static String API_ADD_LOCATION_PEOPLE = SITE + FOLDER + "addLocationPeople.php";
    private final static String API_UPLOAD_PHOTOS = SITE + FOLDER + "upload_images.php";
    private final static String API_ADD_PICTURE = SITE + FOLDER + "addPictures.php";
    private final static String API_GET_PICTURE = SITE + FOLDER + "getPictures.php";
    private final static String API_GET_DETAIL = SITE + FOLDER + "getDetail.php";
    private final static String API_GET_INFO = SITE + FOLDER + "getInfo.php";
    private final static String API_ADD_PERSONNEL = SITE + FOLDER + "addPersonnel.php";
    private final static String API_GET_PERSONNEL = SITE + FOLDER + "getPersonnel.php";
    private final static String API_GET_PERSON_LIST = SITE + FOLDER + "getPersonList.php";
    private final static String API_GET_CATEGORY_SCALE = SITE + FOLDER + "getCategory.php";
    private final static String API_GET_SIMILAR = SITE + FOLDER + "getSimilar.php";
    private final static String API_GET_SEARCH = SITE + FOLDER + "search.php";
    private final static String API_GET_SEARCH_SUGGESTION = SITE + FOLDER + "searchSuggestion.php";
    private final static String API_GET_SEARCH_CATEGORY = SITE + FOLDER + "searchCategory.php";
    private final static String API_GET_ALL_ITEMS = SITE + FOLDER + "getAll.php";
    private final static String API_GET_FILTER_OPTIONS = SITE + FOLDER + "filter.php";
    private final static String API_SEND_ERROR = SITE + FOLDER + "putError.php";
    //JSON
    private final static String API_GET_PROVINCE = SITE + FOLDER + JSON_FOLDER + "city.json";
    private final static String API_GET_CATEGORY = SITE + "json/category.json  ";
    private static final String TAG = "ApiService";
    private JSONObject jsonObjectPhoto;


    private int retryTime = 15000;
    private Context context;


    public ApiService(Context context) {
        this.context = context;
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


    public void getPersonnel(int LOCATION_ID, OnPersonnelReceived onPersonnelReceived) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProvidersApp.KEY_LOCATION_ID, LOCATION_ID);
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
                onPersonnelReceived.onItemReceived(ProvidersApp.KEY_VOLLEY_ERROR, null, error.toString());
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    /*
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
                            onHomeListItemReceived.onItemReceived(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, null, error.toString());
                        }
                    }
            );

            request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(request);
        }
        */
    public void getHomeRecyclerListItems(final OnHomeItems onHomeItems) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_HOME_LIST_ITEMS, null,
                response -> parseJsonHomeRecyclerListItems(response, onHomeItems),
                error -> onHomeItems.onError(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, error)
        );

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getProvince(OnProvinceReceived onProvinceReceived) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_GET_PROVINCE, null, response -> parseJsonProvince(response, onProvinceReceived), error -> onProvinceReceived.onReceived(null, null, error));

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public void getCategory(OnCategoryReceived onCategoryReceived) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API_GET_CATEGORY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJsonCategory(response, onCategoryReceived);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onCategoryReceived.onReceived(null, null, error);

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


        }, error -> onGetDetails.OnGetDetail(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, error.toString()));

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getInfo(boolean isFirstScale, int Post_id, String GROUP_NAME, OnReceivedInfo onReceivedInfo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProvidersApp.KEY_POST_ID, Post_id);
            jsonObject.put(ProvidersApp.GROUP_NAME, GROUP_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_INFO, jsonObject, response -> {

            parseGetInfo(isFirstScale, response, onReceivedInfo);


        }, error -> onReceivedInfo.OnReceived(isFirstScale, ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, error.toString()));

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getPersonList(OnGetPersonList onGetPersonList) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_GET_PERSON_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                parseJsonPersonList(response, onGetPersonList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onGetPersonList.onReceived(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, error.toString());
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy());
        Volley.newRequestQueue(context).add(request);

    }

    public void getCategoryScale(String GROUP_NAME, OnReceivedCategory onReceivedCategory) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ProvidersApp.GROUP_NAME, GROUP_NAME);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_CATEGORY_SCALE, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    parseJsonCategory(response, onReceivedCategory);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onReceivedCategory.onReceived(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, error.toString());
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy());
            Volley.newRequestQueue(context).add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getSimilar(String GROUP_NAME, OnReceivedSimilar onReceivedSimilar) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ProvidersApp.GROUP_NAME, GROUP_NAME);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_SIMILAR, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    parseJsonSimilar(response, onReceivedSimilar, GROUP_NAME);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onReceivedSimilar.onReceivedSmr(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, error.toString());
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy());
            Volley.newRequestQueue(context).add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void addPersonnel(int LOCATION_ID, int PEOPLE_ID, OnAddPersonnel onAddPersonnel) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_ID, Integer.valueOf(LOCATION_ID));
            jsonObject.put(ProvidersApp.KEY_JSON_OBJECT_PEOPLE_ID, Integer.valueOf(PEOPLE_ID));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_ADD_PERSONNEL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObjectResponse = new JSONObject(response.toString());
                    JSONArray jsonArrayResult = jsonObjectResponse.getJSONArray("result");
                    JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(0);
                    boolean isSuccess = Boolean.valueOf(jsonObjectResult.getString("success"));
                    if (isSuccess) {
                        onAddPersonnel.onAdded(ProvidersApp.STATUS_CODE_SUCCESSFULLY, null);
                    } else {
                        String msg = jsonObjectResult.getString("message");
                        onAddPersonnel.onAdded(ProvidersApp.STATUS_CODE_SERVER_ERROR, msg);

                    }
                } catch (JSONException e) {
                    onAddPersonnel.onAdded(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, e.toString());
                }


            }
        }, error -> onAddPersonnel.onAdded(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, error.toString()));

        request.setRetryPolicy(new DefaultRetryPolicy());
        Volley.newRequestQueue(context).add(request);

    }


    public void search(JSONObject jsonObject, OnReceivedSearch onReceivedSearch) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_SEARCH, jsonObject, response -> parseJsonSearch(response, onReceivedSearch), error -> onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, error.toString()));
        request.setRetryPolicy(new DefaultRetryPolicy());
        Volley.newRequestQueue(context).add(request);

    }

    public void getAllItems(OnGetAllItems onGetAllItems) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_ALL_ITEMS, null, response -> parseGetAllItems(response, onGetAllItems), onGetAllItems::OnErrorSearch);
        request.setRetryPolicy(new DefaultRetryPolicy());
        Volley.newRequestQueue(context).add(request);

    }

    public void getFilterOptions(OnGetFilterOptions onGetFilterOptions) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_FILTER_OPTIONS, null, response -> parseGetFilterOptions(response, onGetFilterOptions), onGetFilterOptions::OnErrorFiler);
        request.setRetryPolicy(new DefaultRetryPolicy());
        Volley.newRequestQueue(context).add(request);

    }

    public void searchCategory(JSONObject jsonObject, OnSearchCategory onSearchCategory) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_SEARCH_CATEGORY, jsonObject, response -> parseJsonSearchCategory(response, onSearchCategory), onSearchCategory::OnErrorSearch);
        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getSearchSuggestion(OnReceivedSearch onReceivedSearch) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_SEARCH_SUGGESTION, null, response -> parseJsonSearchSuggestion(response, onReceivedSearch), error -> onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_VOLLEY_ERROR, null, error.toString()));
        request.setRetryPolicy(new DefaultRetryPolicy());
        Volley.newRequestQueue(context).add(request);

    }

    public void sendError(String errorTxt, OnSendError onSendError) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProvidersApp.KEY_ERROR, errorTxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_SEND_ERROR, jsonObject, response -> parseJsonError(response, onSendError), error -> onSendError.ErrorSendError(error));
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
            if (isSuccess) {
                JSONArray itemsArray = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();
                //ArrayList<PictureUpload> pictureUploadList = new ArrayList<>();

                for (int i = 0; i < itemsArray.length(); i++) {

                    JSONObject jsonObjectLocPeo = itemsArray.getJSONObject(i);

                    LocationPeople locationPeople = new LocationPeople();
                    // PictureUpload pictureUpload = new PictureUpload();

                    // locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                    locationPeople.setId(jsonObjectLocPeo.getInt("PEOPLE_ID"));
                    locationPeople.setName(jsonObjectLocPeo.getString("personnelName"));
                    locationPeople.setTag(jsonObjectLocPeo.getString("TagPeople"));
                    locationPeople.setOriginalPic(jsonObjectLocPeo.getString("image"));

                    //pictureUpload.setPic_address(jsonObjectLocPeo.getString("image"));
                    locationPeople.setImageWidth(jsonObjectLocPeo.getInt("width"));
                    locationPeople.setImageHeight(jsonObjectLocPeo.getInt("height"));
                    locationPeople.setImageThumb150(jsonObjectLocPeo.getString("thumb_150"));
                    locationPeople.setImageThumb1000(jsonObjectLocPeo.getString("thumb_1000"));

                    locationPeopleList.add(locationPeople);
                    //pictureUploadList.add(pictureUpload);
                }
                onPersonnelReceived.onItemReceived(ProvidersApp.KEY_SUCCESS, locationPeopleList, null);
            } else {
                String msg = jsonObjectRes.getString("message");
                onPersonnelReceived.onItemReceived(ProvidersApp.KEY_EMPTY_DATA, null, msg);
            }


        } catch (JSONException e) {
            onPersonnelReceived.onItemReceived(ProvidersApp.KEY_JSON_EXCEPTION, null, e.toString());

        }

    }

    private void parseJsonHomeRecyclerListItems(JSONObject response, OnHomeItems onHomeItems) {

        try {
            List<LocationPeople> locationPeoplePerItem = new ArrayList<>();
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


            onHomeItems.onSuccess(locationPeopleListLayout, locationPeoplePerItem);


        } catch (JSONException e) {
            onHomeItems.onError(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, e);

        }

    }

    private void parseJsonProvince(JSONObject response, OnProvinceReceived onProvinceReceived) {

        List<City> cities = new ArrayList<>();
        List<SingleCheckItemsExp> makeSingleCheckParent = new ArrayList<>();

        try {
            JSONArray jsAryItems = response.getJSONArray("items");

            for (int i = 0; i < jsAryItems.length(); i++) {

                JSONObject jsonObject = jsAryItems.getJSONObject(i);
                String provinceName = jsonObject.getString("title");
                JSONArray jsonArrayCities = jsonObject.getJSONArray("child");

                List<ChildExp> makeChild = new ArrayList<>();

                for (int j = 0; j < jsonArrayCities.length(); j++) {
                    JSONObject jsonObjectCity = jsonArrayCities.getJSONObject(j);
                    String cityName = jsonObjectCity.getString("title");

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


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        onProvinceReceived.onReceived(makeSingleCheckParent, cities, null);

    }

    private void parseJsonCategory(JSONArray response, OnCategoryReceived onCategoryReceived) {

        List<Category> categoryList = new ArrayList<>();
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

                    Category category = new Category();
                    category.setParentName(parentName);
                    category.setChildName(childName);
                    category.setImgUrl(imgUrl);

                    categoryList.add(category);


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

        onCategoryReceived.onReceived(makeSingleCheckParent, categoryList, null);

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
            JSONObject jsonObjectRes = jsonObjectResponse.getJSONObject("result");

            boolean isSuccess = jsonObjectRes.getBoolean("success");

            if (isSuccess) {


                JSONArray jsAryItems = jsonObjectRes.getJSONArray("items");
                JSONObject JsObjItems = jsAryItems.getJSONObject(0);

                LocationPeople locPeo = new LocationPeople();
                locPeo.setPhone(JsObjItems.getString("phone"));

                onGetDetails.OnGetDetail(ProvidersApp.STATUS_CODE_SUCCESSFULLY, locPeo, null);


            } else {
                String msg = jsonObjectRes.getString("message");
                onGetDetails.OnGetDetail(ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
            }

        } catch (JSONException e) {
            onGetDetails.OnGetDetail(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, response.toString());


        }
    }

    private void parseGetInfo(boolean isFirstScale, JSONObject response, OnReceivedInfo onReceivedInfo) {
        try {
            JSONObject jsonObjectResponse = new JSONObject(response.toString());
            JSONObject jsonObjectRes = jsonObjectResponse.getJSONObject("result");

            boolean isSuccess = jsonObjectRes.getBoolean("success");

            if (isSuccess) {

                ArrayList<Info> infoList = new ArrayList<>();

                JSONArray jsAryItems = jsonObjectRes.getJSONArray("items");

                for (int i = 0; i < jsAryItems.length(); i++) {

                    JSONObject jsObjItems = jsAryItems.getJSONObject(i);
                    Info info = new Info();
                    info.setSubject(jsObjItems.getString("key"));
                    info.setDescription(jsObjItems.getString("value"));
                    info.setBoolean(jsObjItems.getBoolean("isBoolean"));
                    infoList.add(info);
                }

                //onPhoneReceived.onReceived(JsObjItems.getString("phone"));

                onReceivedInfo.OnReceived(isFirstScale, ProvidersApp.STATUS_CODE_SUCCESSFULLY, infoList, null);


            } else {
                String msg = jsonObjectRes.getString("message");
                onReceivedInfo.OnReceived(isFirstScale, ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
            }

        } catch (JSONException e) {
            onReceivedInfo.OnReceived(isFirstScale, ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, response.toString());


        }
    }

    private void addInfo(ArrayList<Info> infoList, JSONObject jsObjItems, String subject, String titleJson) {

    }

    private void parseJsonPersonList(JSONObject response, OnGetPersonList onGetPersonList) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObjectRes = jsonArray.getJSONObject(0);
            boolean isSuccess = Boolean.valueOf(jsonObjectRes.getString("success"));

            if (isSuccess) {
                JSONArray jsonArrayItems = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();

                for (int i = 0; i < jsonArrayItems.length(); i++) {

                    JSONObject jsonObjectItems = jsonArrayItems.getJSONObject(i);
                    LocationPeople locationPeople = new LocationPeople();
                    locationPeople.setId(jsonObjectItems.getInt("ID"));
                    locationPeople.setName(jsonObjectItems.getString("nameLocPeo"));
                    locationPeople.setTag(jsonObjectItems.getString("tagLocPeo"));
                    locationPeople.setOriginalPic(jsonObjectItems.getString("original_pic"));
                    locationPeople.setImageThumb150(jsonObjectItems.getString("thumb_pic"));

                    locationPeopleList.add(locationPeople);
                }

                onGetPersonList.onReceived(ProvidersApp.STATUS_CODE_SUCCESSFULLY, locationPeopleList, null);

            } else {
                String msg = jsonObjectRes.getString("message");
                onGetPersonList.onReceived(ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            onGetPersonList.onReceived(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, e.toString());

        }
    }

    private void parseJsonCategory(JSONObject response, OnReceivedCategory onReceivedCategory) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObjectRes = jsonArray.getJSONObject(0);
            boolean isSuccess = Boolean.valueOf(jsonObjectRes.getString("success"));

            if (isSuccess) {
                JSONArray jsonArrayItems = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();

                for (int i = 0; i < jsonArrayItems.length(); i++) {

                    JSONObject jsonObjectItems = jsonArrayItems.getJSONObject(i);
                    LocationPeople locationPeople = new LocationPeople();
                    locationPeople.setId(jsonObjectItems.getInt("ID"));
                    locationPeople.setName(jsonObjectItems.getString("nameLocPeo"));
                    locationPeople.setTag(jsonObjectItems.getString("tagLocPeo"));
                    locationPeople.setOriginalPic(jsonObjectItems.getString("original_pic"));
                    locationPeople.setImageThumb150(jsonObjectItems.getString("thumb_pic"));

                    locationPeopleList.add(locationPeople);
                }

                onReceivedCategory.onReceived(ProvidersApp.STATUS_CODE_SUCCESSFULLY, locationPeopleList, null);

            } else {
                String msg = jsonObjectRes.getString("message");
                onReceivedCategory.onReceived(ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            onReceivedCategory.onReceived(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, e.toString());

        }
    }

    private void parseJsonSimilar(JSONObject response, OnReceivedSimilar onReceivedSimilar, String GROUP_NAME) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObjectRes = jsonArray.getJSONObject(0);
            boolean isSuccess = Boolean.valueOf(jsonObjectRes.getString("success"));

            if (isSuccess) {
                JSONArray jsonArrayItems = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();

                for (int i = 0; i < jsonArrayItems.length(); i++) {

                    JSONObject jsonObjectItems = jsonArrayItems.getJSONObject(i);
                    LocationPeople locationPeople = new LocationPeople();
                    locationPeople.setId(jsonObjectItems.getInt("ID"));
                    locationPeople.setName(jsonObjectItems.getString("nameLocPeo"));
                    locationPeople.setTag(jsonObjectItems.getString("tagLocPeo"));
                    locationPeople.setGroup(GROUP_NAME);
                    locationPeople.setOriginalPic(jsonObjectItems.getString("original_pic"));
                    locationPeople.setImageThumb150(jsonObjectItems.getString("thumb_pic"));

                    locationPeopleList.add(locationPeople);
                }

                onReceivedSimilar.onReceivedSmr(ProvidersApp.STATUS_CODE_SUCCESSFULLY, locationPeopleList, null);

            } else {
                String msg = jsonObjectRes.getString("message");
                onReceivedSimilar.onReceivedSmr(ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            onReceivedSimilar.onReceivedSmr(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, e.toString());

        }
    }

    private void parseJsonSearch(JSONObject response, OnReceivedSearch onReceivedSearch) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObjectRes = jsonArray.getJSONObject(0);
            boolean isSuccess = Boolean.valueOf(jsonObjectRes.getString("success"));

            if (isSuccess) {
                JSONArray jsonArrayItems = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();

                for (int i = 0; i < jsonArrayItems.length(); i++) {

                    JSONObject jsonObjectItems = jsonArrayItems.getJSONObject(i);
                    LocationPeople locationPeople = new LocationPeople();
                    locationPeople.setId(jsonObjectItems.getInt("ID"));
                    locationPeople.setName(jsonObjectItems.getString("nameLocPeo"));
                    locationPeople.setTag(jsonObjectItems.getString("tagLocPeo"));
                    locationPeople.setCity(jsonObjectItems.getString("city"));
                    locationPeople.setProvince(jsonObjectItems.getString("province"));
                    locationPeople.setOriginalPic(jsonObjectItems.getString("original_pic"));
                    locationPeople.setImageThumb150(jsonObjectItems.getString("thumb_pic"));

                    locationPeopleList.add(locationPeople);
                }

                onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_SUCCESSFULLY, locationPeopleList, null);

            } else {
                String msg = jsonObjectRes.getString("message");
                onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
            }
        } catch (JSONException e) {
            onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, e.toString());

        }
    }

    private void parseGetAllItems(JSONObject response, OnGetAllItems onGetAllItems) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObjectRes = jsonArray.getJSONObject(0);
            boolean isSuccess = Boolean.valueOf(jsonObjectRes.getString("success"));

            if (isSuccess) {
                JSONArray jsonArrayItems = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();

                for (int i = 0; i < jsonArrayItems.length(); i++) {

                    JSONObject jsonObjectItems = jsonArrayItems.getJSONObject(i);
                    LocationPeople locationPeople = new LocationPeople();
                    locationPeople.setId(jsonObjectItems.getInt("ID"));
                    locationPeople.setName(jsonObjectItems.getString("nameLocPeo"));
                    locationPeople.setTag(jsonObjectItems.getString("tagLocPeo"));
                    locationPeople.setCity(jsonObjectItems.getString("city"));
                    locationPeople.setProvince(jsonObjectItems.getString("province"));
                    locationPeople.setOriginalPic(jsonObjectItems.getString("original_pic"));
                    locationPeople.setImageThumb150(jsonObjectItems.getString("thumb_pic"));

                    locationPeopleList.add(locationPeople);
                }

                // onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_SUCCESSFULLY, locationPeopleList, null);
                onGetAllItems.OnSuccessSearch(locationPeopleList);

            } else {
                String msg = jsonObjectRes.getString("message");
                //  onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
                onGetAllItems.OnErrorSearch(msg);
            }
        } catch (JSONException e) {
            // onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, e.toString());
            onGetAllItems.OnErrorSearch(e);

        }
    }

    private void parseGetFilterOptions(JSONObject response, OnGetFilterOptions onGetFilterOptions) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            ArrayList<Filter> filterList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsObjResult = jsonArray.getJSONObject(i);
                String title = jsObjResult.getString("title");
                String tag = jsObjResult.getString("tag");
                JSONObject jsObjData = jsObjResult.getJSONObject("data");

                filterList.add(new Filter(i, title, tag, jsObjData));
            }

            onGetFilterOptions.OnSuccessFilter(filterList);


        } catch (JSONException e) {
            onGetFilterOptions.OnErrorFiler(e);

        }
    }

    private void parseJsonSearchCategory(JSONObject response, OnSearchCategory onSearchCategory) {

        try {
            ArrayList<GroupModel> groupModels = new ArrayList<>();
            ArrayList<LocationPeople> childModels;
            String titleGroup;

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray arrResult = jsonObject.getJSONArray("result");


            for (int i = 0; i < arrResult.length(); i++) {

                JSONObject objCategory = arrResult.getJSONObject(i);
                titleGroup = objCategory.getString("title");
                JSONArray arrData = objCategory.getJSONArray("data");
                JSONObject objData = arrData.getJSONObject(0);
                boolean isSuccess = objData.getBoolean("success");

                if (isSuccess) {
                    JSONArray arrItems = objData.getJSONArray("items");

                    childModels = new ArrayList<>();

                    for (int j = 0; j < arrItems.length(); j++) {

                        JSONObject objItem = arrItems.getJSONObject(j);

                        LocationPeople locationPeople = new LocationPeople();
                        locationPeople.setId(objItem.getInt("ID"));
                        locationPeople.setName(objItem.getString("nameLocPeo"));
                        locationPeople.setTag(objItem.getString("tagLocPeo"));
                        locationPeople.setGroup(objItem.getString("group_name"));
                        locationPeople.setCity(objItem.getString("city"));
                        locationPeople.setAd(objItem.getString("is_ad"));
                        locationPeople.setOriginalPic(objItem.getString("original_pic"));
                        locationPeople.setImageThumb150(objItem.getString("thumb_pic"));

                        childModels.add(locationPeople);

/*
                        childModels.add(new ChildModel.Builder()
                                .setId(objItem.getInt("ID"))
                                .setName(objItem.getString("nameLocPeo"))
                                .setCategory(objItem.getString("tagLocPeo"))
                                .setGroup(objItem.getString("group_name"))
                                .setCity(objItem.getString("city"))
                                .setIsAd(objItem.getString("is_ad"))
                                .setOriginalPic(objItem.getString("original_pic"))
                                .setThumb_pic(objItem.getString("thumb_pic"))
                                .create());*/


                    }
                    groupModels.add(new GroupModel(titleGroup, childModels));
                    onSearchCategory.OnSuccessSearch(groupModels);
                } else {
                    onSearchCategory.OnErrorSearch(objData.getString("message"));
                }

            }

        } catch (JSONException e) {
            onSearchCategory.OnErrorSearch(e);

        }
    }

    private void parseJsonSearchSuggestion(JSONObject response, OnReceivedSearch onReceivedSearch) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObjectRes = jsonArray.getJSONObject(0);
            boolean isSuccess = Boolean.valueOf(jsonObjectRes.getString("success"));

            if (isSuccess) {
                JSONArray jsonArrayItems = jsonObjectRes.getJSONArray("items");

                ArrayList<LocationPeople> locationPeopleList = new ArrayList<>();

                for (int i = 0; i < jsonArrayItems.length(); i++) {

                    JSONObject jsonObjectItems = jsonArrayItems.getJSONObject(i);
                    LocationPeople locationPeople = new LocationPeople();
                    locationPeople.setId(jsonObjectItems.getInt("ID"));
                    locationPeople.setName(jsonObjectItems.getString("nameLocPeo"));
                    locationPeople.setTag(jsonObjectItems.getString("tagLocPeo"));
                    locationPeople.setCity(jsonObjectItems.getString("city"));
                    locationPeople.setProvince(jsonObjectItems.getString("province"));
                    locationPeople.setOriginalPic(jsonObjectItems.getString("original_pic"));
                    locationPeople.setImageThumb150(jsonObjectItems.getString("thumb_pic"));

                    locationPeopleList.add(locationPeople);
                }

                onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_SUCCESSFULLY, locationPeopleList, null);

            } else {
                String msg = jsonObjectRes.getString("message");
                onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_SERVER_ERROR, null, msg);
            }
        } catch (JSONException e) {
            onReceivedSearch.onSearch(ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR, null, e.toString());

        }
    }

    private void parseJsonError(JSONObject response, OnSendError onSendError) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject jsObjResult = jsonObject.getJSONObject("result");

            if (jsObjResult.getBoolean("success")) {
                onSendError.SuccessSendError();
            } else {
                onSendError.ErrorSendError(jsObjResult.getString("message"));
            }

        } catch (JSONException e) {
            onSendError.ErrorSendError(e);
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
        void onItemReceived(int statusCode, List<ListLayout> listLayouts, List<LocationPeople> locationPeopleList, String error);
    }

    public interface OnHomeItems {
        void onSuccess(List<ListLayout> listLayouts, List<LocationPeople> locationPeopleList);

        void onError(int statusCode, Object error);
    }

    public interface OnGroupItemReceived {
        void onItemGroupReceived(ArrayList<LocationPeople> locationPeopleList, VolleyError error);
    }


    public interface OnProvinceReceived {
        void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent, List<City> cities, VolleyError error);
    }

    public interface OnCategoryReceived {
        void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent, List<Category> tags, VolleyError error);
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
        void OnGetDetail(int statusCode, LocationPeople locationPeople, String error);
    }

    public interface OnReceivedInfo {
        void OnReceived(boolean isFirstScale, int statusCode, ArrayList<Info> infoList, String error);
    }

    public interface OnGetPersonList {
        void onReceived(int successCode, ArrayList<LocationPeople> locationPeopleList, String error);
    }

    public interface OnPersonnelReceived {
        void onItemReceived(int status, ArrayList<LocationPeople> locationPeopleList, String error);
    }

    public interface OnAddPersonnel {
        void onAdded(int successCode, String error);
    }

    public interface OnReceivedCategory {
        void onReceived(int successCode, ArrayList<LocationPeople> locationPeopleList, String error);
    }

    public interface OnReceivedSimilar {
        void onReceivedSmr(int statusCode, ArrayList<LocationPeople> locationPeopleList, String error);
    }

    public interface OnReceivedSearch {
        void onSearch(int statusCode, ArrayList<LocationPeople> locationPeopleList, String error);
    }

    public interface OnSearchCategory {
        void OnSuccessSearch(ArrayList<GroupModel> groupModels);

        void OnErrorSearch(Object error);
    }

    public interface OnGetAllItems {
        void OnSuccessSearch(ArrayList<LocationPeople> locationPeopleList);

        void OnErrorSearch(Object error);
    }

    public interface OnGetFilterOptions {
        void OnSuccessFilter(ArrayList<Filter> filterList);

        void OnErrorFiler(Object error);
    }

    public interface OnSendError {
        void SuccessSendError();

        void ErrorSendError(Object error);
    }
}