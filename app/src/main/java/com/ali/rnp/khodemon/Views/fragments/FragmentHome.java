package com.ali.rnp.khodemon.Views.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.LinearSingleAdapter;
import com.ali.rnp.khodemon.Adapter.LocationPeopleAdapter;
import com.ali.rnp.khodemon.Adapter.SingleItemAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.BannerSlider.MainSliderAdapter;
import com.ali.rnp.khodemon.BannerSlider.PicassoImageLoadingService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activites.MainActivity;
import com.android.volley.VolleyError;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.transition.TransitionInflater;
import ss.com.bannerslider.Slider;


public class FragmentHome extends Fragment implements
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        LinearSingleAdapter.ItemClickListenerRecyclerList
{

    private Slider slider;
    private ProgressBar progressBar;
    private CoordinatorLayout rootLayout;
    private MyEditText searchEditText;
    private RecyclerView recyclerView;
    private ImageView locationImageView;
    private AHBottomNavigation bottomNavigation;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CardView peopleCardView;
    private CardView locationCardView;

    private ConstraintLayout locationConstraintLayout;
    private ConstraintLayout peopleConstraintLayout;

    private FragmentManager fragmentManager;
    private FragmentGroup fragmentGroup;
    private FragmentTransaction fragmentTrGroup;

    public static final String GROUP_KEY = "GROUP_KEY";

    private Context context;



    private static final String TAG = "FragmentHome";




    public FragmentHome() {

    }

    @SuppressLint("ValidFragment")
    public FragmentHome(Context context) {
        this.context = context;
    }

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        initViews(rootView);
        SetupBannerSlider(rootView);
        SetupRecyclerViewHomeItems();
        SetupFragments();





        return rootView;
    }



    private void SetupFragments() {

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentGroup = FragmentGroup.newInstance();

    }

    private void SetupRecyclerViewHomeItems() {

        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "\n dx "+dx+"\n dy" +dy);
            }
        });


        ApiService apiService = new ApiService(context);
        final SingleItemAdapter singleItemAdapter = new SingleItemAdapter(context);
        final LinearSingleAdapter linearSingleAdapter = new LinearSingleAdapter(context,this);

        new Handler().postDelayed(() -> {

            apiService.getHomeRecyclerListItems((homeLists, locationPeopleList, error) -> {

                progressBar.setVisibility(View.INVISIBLE);

                Activity activity = getActivity();

                if (activity != null && homeLists != null && locationPeopleList != null){
                    singleItemAdapter.setListDataForAdapter(locationPeopleList);
                    linearSingleAdapter.setListDataForAdapter(homeLists);
                    recyclerView.setAdapter(linearSingleAdapter);




                }else {
                    Log.i(TAG, "onItemReceived: Error");
                }

            });

        },1);
    }


    private void SetupBannerSlider(View rootView) {
        Slider.init(new PicassoImageLoadingService(getContext()));
        slider = rootView.findViewById(R.id.fragment_home_MainSlider);

        slider.setAdapter(new MainSliderAdapter());


    }


    @SuppressLint("ClickableViewAccessibility")
    private void initViews(View rootView) {

        recyclerView = rootView.findViewById(R.id.fragment_home_recyclerView_homeItems);
        swipeRefreshLayout = rootView.findViewById(R.id.fragment_home_swipeRefreshLayout);
        searchEditText = rootView.findViewById(R.id.fragment_home_editText_search);
        progressBar = rootView.findViewById(R.id.fragment_home_progress_bar);
        locationCardView = rootView.findViewById(R.id.fragment_home_location_cardView);
        peopleCardView = rootView.findViewById(R.id.fragment_home_people_cardView);

        locationConstraintLayout = rootView.findViewById(R.id.fragment_home_location_constraintLayout);
        peopleConstraintLayout = rootView.findViewById(R.id.fragment_home_people_constraintLayout);

        bottomNavigation = getActivity().findViewById(R.id.bottom_navigation);

        swipeRefreshLayout.setColorSchemeColors(
                ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null),
                ResourcesCompat.getColor(getResources(),R.color.price_red,null),
                ResourcesCompat.getColor(getResources(),R.color.price_green,null));


        swipeRefreshLayout.setOnRefreshListener(this);

        searchEditText.setOnClickListener(this);

        locationConstraintLayout.setOnClickListener(this);
        peopleConstraintLayout.setOnClickListener(this);

        locationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        peopleCardView.setOnClickListener(this);





    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;


    }



    @Override
    public void onRefresh() {


        ApiService apiService = new ApiService(context);
        apiService.getHomeItems(new ApiService.OnHomeItemReceived() {
            @Override
            public void onItemReceived(List<LocationPeople> locationPeopleList,VolleyError error) {

                // TODO add if activity != null

                swipeRefreshLayout.setRefreshing(false);

                if (locationPeopleList != null ){
                    LocationPeopleAdapter locationPeopleAdapter = new LocationPeopleAdapter(context);
                    locationPeopleAdapter.setListDataForAdapter(locationPeopleList);
                    recyclerView.setAdapter(locationPeopleAdapter);
                }else {
                    Toast.makeText(context, "Error Connection", Toast.LENGTH_SHORT).show();

                }



            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.fragment_home_location_constraintLayout:
                Bundle argsLocation = new Bundle();
                argsLocation.putString(GROUP_KEY,ApiService.GROUP_NAME_LOCATION);
                fragmentGroup.setArguments(argsLocation);
                fragmentGroup.setArguments(argsLocation);

                fragmentGroupReplace();

                break;

            case R.id.fragment_home_people_constraintLayout:
                Bundle argsPeople = new Bundle();
                argsPeople.putString(GROUP_KEY,ApiService.GROUP_NAME_PEOPLE);
                fragmentGroup.setArguments(argsPeople);

                fragmentGroupReplace();

                break;

            case R.id.fragment_home_location_cardView:

                fragmentGroupReplace();

                break;

            case R.id.fragment_home_people_cardView:

                fragmentGroupReplace();

                break;

            case R.id.fragment_home_editText_search:

                FragmentSearch fragmentSearch = FragmentSearch.newInstance();
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(searchEditText, ViewCompat.getTransitionName(searchEditText))
                        .replace(R.id.mainActivity_fragment_container, fragmentSearch)
                        .commit();

                bottomNavigation.setCurrentItem(MainActivity.BOTTOM_NAV_ITEM_SEARCH);

/*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomNavigation.setCurrentItem(MainActivity.BOTTOM_NAV_ITEM_SEARCH,false);
                    }
                },600);
*/


        }
    }

    private void fragmentGroupReplace() {

        fragmentTrGroup = fragmentManager.beginTransaction();
        fragmentTrGroup.replace(R.id.mainActivity_fragment_container,fragmentGroup);
        fragmentTrGroup.addToBackStack("d");
        fragmentTrGroup.commit();
    }

    @Override
    public void onItemClick(int position, String group) {
        switch (group){
            case ApiService.GROUP_NAME_LOCATION:
                Bundle argsLocation = new Bundle();
                argsLocation.putString(GROUP_KEY,ApiService.GROUP_NAME_LOCATION);
                fragmentGroup.setArguments(argsLocation);
                fragmentGroup.setArguments(argsLocation);

                fragmentGroupReplace();
                break;

            case ApiService.GROUP_NAME_PEOPLE:
                Bundle argsPeople = new Bundle();
                argsPeople.putString(GROUP_KEY,ApiService.GROUP_NAME_PEOPLE);
                fragmentGroup.setArguments(argsPeople);

                fragmentGroupReplace();
                break;



        }
    }
}
