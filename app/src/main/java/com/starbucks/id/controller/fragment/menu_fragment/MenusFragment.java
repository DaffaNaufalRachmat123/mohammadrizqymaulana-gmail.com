package com.starbucks.id.controller.fragment.menu_fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.menu_fragment.adapter.BrowseMenuAdapter;
import com.starbucks.id.controller.fragment.menu_fragment.adapter.BrowseMenuSearchAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.menu.MenuModel;
import com.starbucks.id.model.menu.MenuSubModel;
import com.starbucks.id.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenusFragment extends Fragment {
    private ImageView imgBvg;
    private ImageView imgFood;
    private ImageView imgBean;
    private ImageView imgVia;
    private ImageView imgMerchant;
    private ProgressBar pb;
    private ExpandableListView lstBrowseMenu;

    private ConstraintLayout CLContainer, RVContainer;
    private UserDefault userDefault;
    private List<MenuModel> menuList;
    private Picasso picasso;
    private TextView tvBvg, tvVia, tvBean, tvFood, tvMerchand;
    private MainActivity activity;
    private FirebaseAnalytics mFirebaseAnalytics;
    public MenusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        if (activity != null) picasso = activity.getPicasso();
        setHasOptionsMenu(true);
        activity.counter = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menus, container, false);

        imgBvg = view.findViewById(R.id.imgBvg);
        imgFood = view.findViewById(R.id.imgFood);
        imgBean = view.findViewById(R.id.imgBean);
        imgVia = view.findViewById(R.id.imgVia);
        imgMerchant = view.findViewById(R.id.imgMerchant);
        lstBrowseMenu = view.findViewById(R.id.lstBrowseMenu);
        CLContainer = view.findViewById(R.id.CLContainer);
        RVContainer = view.findViewById(R.id.RVContainer);
        pb = view.findViewById(R.id.pb);

        tvBean = view.findViewById(R.id.tvBean);
        tvBvg = view.findViewById(R.id.tvBvg);
        tvFood = view.findViewById(R.id.tvFood);
        tvMerchand = view.findViewById(R.id.tvMerchand);
        tvVia = view.findViewById(R.id.tvVia);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle params = new Bundle();
        params.putString("FragmentMenus", "Fragment Menus");
        mFirebaseAnalytics.logEvent("AOS", params);

        setData();

        return view;
    }

    /*UI Controller*/
    private void setData() {
        if (activity.menuList != null) {
            menuList = activity.menuList;
            setBaseView();
        } else getMenuData();
    }

    protected void setBaseView() {
        activity.setToolbarTitle(getString(R.string.f_order_t));
        CLContainer.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);
        RVContainer.setVisibility(View.VISIBLE);

        if (getActivity() != null) {
            BrowseMenuAdapter adapter = new BrowseMenuAdapter(getActivity(), menuList);
            lstBrowseMenu.setAdapter(adapter);
            lstBrowseMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Fragment newFragment = MenuDetailFragment.newInstance(menuList.get(groupPosition).
                            getSubmenu().get(childPosition).getDtlmenu(), menuList.get(groupPosition).getSubmenu().get(childPosition).getNameEn());
                    activity.cFragmentWithBS(newFragment);
                    return true;
                }
            });

            for (int i = 0; i < menuList.size(); i++) {
                final MenuModel menuModel = menuList.get(i);

                switch (menuModel.getMenusNameEn().toLowerCase()) {
                    case "beverages":
                        picasso.load(menuModel.getImageHdr())
                                .into(imgBvg, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        imgBvg.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        picasso.load(R.drawable.defaultpage_720_h).into(imgBvg);
                                        imgBvg.setVisibility(View.VISIBLE);
                                    }
                                });

                        imgBvg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (menuModel.getSubmenu().size() > 0) {
                                    Fragment newFragment = MenuSubFragment.newInstance(menuModel.getSubmenu(),
                                            menuModel.getMenusNameEn());
                                    activity.cFragmentWithBS(newFragment);
                                } else {
                                    Toast.makeText(getActivity(), "No Detail Available.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        break;

                    case "foods":
                        picasso.load(menuModel.getImageHdr()).fit()
                                .into(imgFood, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        imgFood.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        picasso.load(R.drawable.defaultpage_720_h).into(imgFood);
                                        imgFood.setVisibility(View.VISIBLE);

                                    }
                                });

                        imgFood.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (menuModel.getSubmenu().size() > 0) {
                                    Fragment newFragment = MenuSubFragment.newInstance(menuModel.getSubmenu(),
                                            menuModel.getMenusNameEn());
                                    activity.cFragmentWithBS(newFragment);
                                } else {
                                    Toast.makeText(getActivity(), "No Detail Available.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        break;
                    case "whole bean":
                        picasso.load(menuModel.getImageHdr()).fit()
                                .into(imgBean, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        imgBean.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        picasso.load(R.drawable.defaultpage_720_h).into(imgBean);
                                        imgBean.setVisibility(View.VISIBLE);

                                    }
                                });
                        imgBean.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (menuModel.getSubmenu().size() > 0) {
                                    Fragment newFragment = MenuSubFragment.newInstance(menuModel.getSubmenu(), menuModel.getMenusNameEn());
                                    activity.cFragmentWithBS(newFragment);
                                } else {
                                    Toast.makeText(getActivity(), "No Detail Available.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        break;
                    case "via":
                        picasso.load(menuModel.getImageHdr()).fit()
                                .into(imgVia, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        imgVia.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        picasso.load(R.drawable.defaultpage_720_h).into(imgVia);
                                        imgVia.setVisibility(View.VISIBLE);

                                    }
                                });
                        imgVia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (menuModel.getSubmenu().size() > 0) {
                                    Fragment newFragment = MenuSubFragment.newInstance(menuModel.getSubmenu(), menuModel.getMenusNameEn());
                                    activity.cFragmentWithBS(newFragment);
                                } else {
                                    Toast.makeText(getActivity(), "No Detail Available.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        break;
                    case "merchandise":
                        picasso.load(menuModel.getImageHdr()).fit()
                                .into(imgMerchant, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        imgMerchant.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        picasso.load(R.drawable.defaultpage_720_h).into(imgMerchant);
                                        imgMerchant.setVisibility(View.VISIBLE);
                                    }
                                });
                        imgMerchant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (menuModel.getSubmenu().size() > 0) {
                                    Fragment newFragment = MenuSubFragment.newInstance(menuModel.getSubmenu(), menuModel.getMenusNameEn());
                                    activity.cFragmentWithBS(newFragment);
                                } else {
                                    Toast.makeText(getActivity(), "No Detail Available.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        break;

                }

            }
        }

        activity.invalidateOptionsMenu();

        defaultView();
    }

    private void defaultView() {
        BrowseMenuAdapter adapter = new BrowseMenuAdapter(getActivity(), menuList);
        lstBrowseMenu.setAdapter(adapter);
        lstBrowseMenu.setOnGroupClickListener(null);
        setLang();
    }

    private void setLang() {
        if (userDefault.IDLanguage()) {
            tvBvg.setText(activity.getString(R.string.id_beverage));
            tvFood.setText(activity.getString(R.string.id_food));
            tvVia.setText(activity.getString(R.string.via));
            tvMerchand.setText(activity.getString(R.string.merchandise));
            tvBean.setText(activity.getString(R.string.id_bean));
        } else {
            tvBvg.setText(activity.getString(R.string.en_beverage));
            tvFood.setText(activity.getString(R.string.en_food));
            tvVia.setText(activity.getString(R.string.via));
            tvMerchand.setText(activity.getString(R.string.merchandise));
            tvBean.setText(activity.getString(R.string.bean));
        }
    }

    private void searchView(String filter) {
        final List<MenuModel> MenusSearchResult = new ArrayList<>();
        List<MenuModel> MenusSearch = new ArrayList<>();
        ArrayList<String> arrMenus = new ArrayList<>();
        ArrayList<String> arrImgUrl = new ArrayList<>();
        ArrayList<String> arrContent = new ArrayList<>();

        if (menuList != null && menuList.size() > 0) {
            for (int i = 0; i < menuList.size(); i++) {
                if (menuList.get(i).getSubmenu().size() > 0) {
                    for (int x = 0; x < menuList.get(i).getSubmenu().size(); x++) {
                        for (int d = 0; d < menuList.get(i).getSubmenu().get(x).getDtlmenu().size(); d++) {
                            arrMenus.add(menuList.get(i).getSubmenu().get(x).getDtlmenu().get(d).getDtlNameEn());
                            arrImgUrl.add(menuList.get(i).getSubmenu().get(x).getDtlmenu().get(d).getImageDtlContent());
                            arrContent.add(menuList.get(i).getSubmenu().get(x).getDtlmenu().get(d).getMenuContentEn());
                        }
                    }
                }

            }
        }

        List<MenuSubModel> arrDetail;
        for (int i = 0; i < arrMenus.size(); i++) {
            arrDetail = new ArrayList<>();
            MenuModel m = new MenuModel();

            MenuSubModel s = new MenuSubModel();
            s.setImageSub(arrImgUrl.get(i) == null ? "" : arrImgUrl.get(i));
            s.Content = arrMenus.get(i) == null ? "" : arrMenus.get(i);

            arrDetail.add(s);
            m.setSubmenu(arrDetail);
            m.setImageHdr(arrImgUrl.get(i) == null ? "" : arrImgUrl.get(i));
            m.setMenusNameEn(arrMenus.get(i) == null ? "" : arrMenus.get(i));
            m.setMenusHdrId(String.valueOf(i));
            MenusSearch.add(m);
        }


        for (int i = 0; i < MenusSearch.size(); i++) {
            if (MenusSearch.get(i).getMenusNameEn().toLowerCase().contains(filter)) {
                MenusSearchResult.add(MenusSearch.get(i));
            }

        }

        BrowseMenuSearchAdapter adapter = new BrowseMenuSearchAdapter(getActivity(), MenusSearchResult);
        lstBrowseMenu.setAdapter(adapter);

        lstBrowseMenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Fragment newFragment = MenuContentFragment.newInstance(MenusSearchResult.get(groupPosition).
                                getSubmenu().get(0).getImageSub(), MenusSearchResult.get(groupPosition).getMenusNameEn(),
                        MenusSearchResult.get(groupPosition).getSubmenu().get(0).Content);

                activity.cFragmentWithBS(newFragment);
                return true;
            }
        });
    }


    /*REST Call*/
    // Get Menu List Data
    public void getMenuData() {
        if (!activity.checkConnection()) return;
        ApiInterface apiService = activity.getApiService();

        Call<List<MenuModel>> call = apiService.getMenuList(DataUtil.encrypt(getString(R.string.rest_content_auth)));

        call.enqueue(new Callback<List<MenuModel>>() {
            @Override
            public void onResponse(Call<List<MenuModel>> call, Response<List<MenuModel>> response) {
                if (getActivity() != null) {
                    menuList = response.body();
                    activity.menuList = menuList;
                    setBaseView();
                }
            }

            @Override
            public void onFailure(Call<List<MenuModel>> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        final SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        final ImageView closeButton = searchView.findViewById(R.id.search_close_btn);

        searchEditText.setHint(userDefault.IDLanguage() ? "CARI MENU KAMI" : "BROWSE OUR MENU");
        searchEditText.setHintTextColor(getResources().getColor(R.color.gray_dop));

        searchView.setMaxWidth(Integer.MAX_VALUE);//set search menu as full width
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (!searchEditText.getText().toString().trim().isEmpty() &&
                        !searchEditText.getText().toString().trim().equals("")) {
                    searchView(searchEditText.getText().toString());
                    return true;
                } else return false;
            }
        });

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty())
                    CLContainer.setVisibility(View.VISIBLE);
                if (newText.equals("")) {
                    BrowseMenuAdapter adapter = new BrowseMenuAdapter(getActivity(), menuList);
                    lstBrowseMenu.setAdapter(adapter);
                    lstBrowseMenu.setOnGroupClickListener(null);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };

        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");

                //Clear query
                searchView.setQuery("", false);

                //Collapse the action view
                searchView.onActionViewCollapsed();

                //Collapse the search widget
                searchItem.collapseActionView();

                searchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

                CLContainer.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) CLContainer.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(queryTextListener);

        // Set Visibility to menu item if data is null
        if(menuList != null) menu.findItem(R.id.action_search).setVisible(true);
        else menu.findItem(R.id.action_search).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

}
