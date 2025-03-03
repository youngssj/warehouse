package com.victor.base.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.victor.base.R;
import com.victor.base.widget.EmptyRecyclerView;
import com.victor.base.widget.y_recycleradapter.GeneralRecyclerViewHolder;
import com.victor.base.widget.y_recycleradapter.Y_ItemEntityList;
import com.victor.base.widget.y_recycleradapter.Y_MultiRecyclerAdapter;
import com.victor.base.widget.y_recycleradapter.Y_OnBind;
import com.zyyoona7.popup.EasyPopup;

import java.util.List;

public class PopUtils {

    EasyPopup mTopPop;
    EasyPopup mBottomPop;

    //    public void showTopPops(Activity activity, List<String> items, String defaultItem, View rootView, ViewGroup dimView, OnPopItemClickListener onPopItemClickListener) {
//        if (items == null || items.size() == 0) {
//            return;
//        }
//        mTopPop = EasyPopup.create()
//                .setContentView(activity, R.layout.logis_view_pop_top, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setAnimationStyle(R.style.CenterPopAnim)
//                //是否允许点击PopupWindow之外的地方消失
//                .setFocusAndOutsideEnable(true)
//                //允许背景变暗
//                .setBackgroundDimEnable(true)
//                //变暗的透明度(0-1)，0为完全透明
//                .setDimValue(0.4f)
//                //变暗的背景颜色
//                .setDimColor(Color.BLACK)
//                //指定任意 ViewGroup 背景变暗
//                .setDimView(dimView)
//                .apply();
//
//        EmptyRecyclerView rcItems = mTopPop.findViewById(R.id.rcItems);
//
//        Y_ItemEntityList itemEntityListItems = new Y_ItemEntityList();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.mContext);
//        rcItems.setLayoutManager(linearLayoutManager);
//        Y_MultiRecyclerAdapter itemsAdapter = new Y_MultiRecyclerAdapter(activity, itemEntityListItems);
//        rcItems.setAdapter(itemsAdapter);
//        itemEntityListItems.addItems(R.layout.logis_item_pop_top, items)
//                .addOnBind(R.layout.logis_item_pop_top, new Y_OnBind() {
//                    @Override
//                    public void onBindChildViewData(GeneralRecyclerViewHolder holder, Object itemData, int position, List<Object> payloads) {
//                        TextView tvItem = holder.getChildView(R.id.tvItem);
//                        tvItem.setText((String) itemData);
//                        if (defaultItem != null && defaultItem.equals((String) itemData)) {
//                            tvItem.setTextColor(activity.getResources().getColor(R.color.color_4977fc));
//                        } else {
//                            tvItem.setTextColor(activity.getResources().getColor(R.color.color_333333));
//                        }
//                        ViewDoubleHelper.hookView(tvItem);
//                        tvItem.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (onPopItemClickListener != null) {
//                                    onPopItemClickListener.onItemClick((String) itemData, position);
//                                }
//                                if (mTopPop.isShowing()) {
//                                    mTopPop.dismiss();
//                                }
//                            }
//                        });
//                    }
//                });
//        mTopPop.showAsDropDown(rootView);
//    }
//
    public void showBottomPops(Activity activity, List<String> items, String defaultItem, View rootView, ViewGroup dimView, OnPopItemClickListener onPopItemClickListener) {
        showBottomPops(activity, items, defaultItem, rootView, dimView, null, onPopItemClickListener);
    }

    public void showBottomPops(Activity activity, List<String> items, String defaultItem, View rootView, ViewGroup dimView, View topView, OnPopItemClickListener onPopItemClickListener) {
        if (items == null || items.size() == 0) {
            return;
        }

        mBottomPop = EasyPopup.create()
                .setContentView(activity, R.layout.view_pop_bottom, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView(dimView)
                .apply();

        if (topView != null) {
            LinearLayout llContentView = mBottomPop.findViewById(R.id.llContentView);
            llContentView.addView(topView, 0);
        }

        EmptyRecyclerView rcItems = mBottomPop.findViewById(R.id.rcItems);

        Y_ItemEntityList itemEntityListItems = new Y_ItemEntityList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        rcItems.setLayoutManager(linearLayoutManager);
        Y_MultiRecyclerAdapter itemsAdapter = new Y_MultiRecyclerAdapter(activity, itemEntityListItems);
        rcItems.setAdapter(itemsAdapter);
        itemEntityListItems.addItems(R.layout.item_pop, items)
                .addOnBind(R.layout.item_pop, new Y_OnBind() {
                    @Override
                    public void onBindChildViewData(GeneralRecyclerViewHolder holder, Object itemData, int position, List<Object> payloads) {
                        String item = (String) itemData;
                        TextView tvItem = holder.getChildView(R.id.tvItem);
                        View line = holder.getChildView(R.id.line);
                        tvItem.setText(item);
                        if (defaultItem != null && defaultItem.equals(item)) {
                            tvItem.setTextColor(activity.getColor(R.color.colorPrimary));
                        } else {
                            tvItem.setTextColor(activity.getColor(R.color.color_333333));
                        }
                        if (items.size() == 1) {
                            tvItem.setBackground(activity.getDrawable(R.drawable.selector_pop_single_btn));
                        } else if (items.size() > 0 && position != items.size() - 1) {
                            if (position == 0) {
                                tvItem.setBackground(activity.getDrawable(R.drawable.selector_pop_top_btn));
                            } else {
                                tvItem.setBackground(activity.getDrawable(R.drawable.selector_pop_middle_btn));
                            }
                        } else {
                            tvItem.setBackground(activity.getDrawable(R.drawable.selector_pop_bottom_btn));
                        }
                        tvItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (onPopItemClickListener != null) {
                                    boolean dismiss = onPopItemClickListener.onItemClick(item, position);
                                    if (dismiss && mBottomPop.isShowing()) {
                                        mBottomPop.dismiss();
                                    }
                                } else if (mBottomPop.isShowing()) {
                                    mBottomPop.dismiss();
                                }
                            }
                        });
                        if (position == items.size() - 1) {
                            line.setVisibility(View.GONE);
                        } else {
                            line.setVisibility(View.VISIBLE);
                        }
                    }
                });
        mBottomPop.findViewById(R.id.tvCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomPop.isShowing()) {
                    mBottomPop.dismiss();
                }
            }
        });
        mBottomPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    //
//    public void showOnlyItemsBottomPops(Activity activity, List<String> items, String defaultItem, View rootView, ViewGroup dimView, View topView, OnPopItemClickListener onPopItemClickListener) {
//        if (items == null || items.size() == 0) {
//            return;
//        }
//
//        // 计算整体高度
//        int totalHeight = items.size() * AndroidUtil.dp2px(50) + AndroidUtil.dp2px(68);
//        int screenHeight = ScreenUtils.getScreenHeight(activity);
//        if (totalHeight > screenHeight - AndroidUtil.dp2px(100)) {
//            totalHeight = screenHeight - AndroidUtil.dp2px(100);
//        }
//
//        mBottomPop = EasyPopup.create()
//                .setContentView(activity, R.layout.logis_view_only_items_pop_bottom, ViewGroup.LayoutParams.MATCH_PARENT, totalHeight)
//                .setAnimationStyle(R.style.BottomPopAnim)
//                //是否允许点击PopupWindow之外的地方消失
//                .setFocusAndOutsideEnable(true)
//                //允许背景变暗
//                .setBackgroundDimEnable(true)
//                //变暗的透明度(0-1)，0为完全透明
//                .setDimValue(0.4f)
//                //变暗的背景颜色
//                .setDimColor(Color.BLACK)
//                //指定任意 ViewGroup 背景变暗
//                .setDimView(dimView)
//                .apply();
//
//        if (topView != null) {
//            LinearLayout llContentView = mBottomPop.findViewById(R.id.llContentView);
//            llContentView.addView(topView, 0);
//        }
//
//        EmptyRecyclerView rcItems = mBottomPop.findViewById(R.id.rcItems);
//
//        Y_ItemEntityList itemEntityListItems = new Y_ItemEntityList();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.mContext);
//        rcItems.setLayoutManager(linearLayoutManager);
//        Y_MultiRecyclerAdapter itemsAdapter = new Y_MultiRecyclerAdapter(activity, itemEntityListItems);
//        rcItems.setAdapter(itemsAdapter);
//        itemEntityListItems.addItems(R.layout.logis_item_pop, items)
//                .addOnBind(R.layout.logis_item_pop, new Y_OnBind() {
//                    @Override
//                    public void onBindChildViewData(GeneralRecyclerViewHolder holder, Object itemData, int position, List<Object> payloads) {
//                        TextView tvItem = holder.getChildView(R.id.tvItem);
//                        View line = holder.getChildView(R.id.line);
//                        tvItem.setText((String) itemData);
//                        if (defaultItem != null && defaultItem.equals((String) itemData)) {
//                            tvItem.setTextColor(SkinCompatResources.getColor(App.mContext, R.color.color_4977fc));
//                        } else {
//                            tvItem.setTextColor(SkinCompatResources.getColor(App.mContext, R.color.color_333333));
//                        }
//                        if (items.size() == 1) {
//                            tvItem.setBackground(SkinCompatResources.getDrawable(App.mContext, R.drawable.selector_pop_single_btn));
//                        } else if (items.size() > 0 && position != items.size() - 1) {
//                            if (position == 0) {
//                                tvItem.setBackground(SkinCompatResources.getDrawable(App.mContext, R.drawable.selector_pop_top_btn));
//                            } else {
//                                tvItem.setBackground(SkinCompatResources.getDrawable(App.mContext, R.drawable.selector_pop_middle_btn));
//                            }
//                        } else {
//                            tvItem.setBackground(SkinCompatResources.getDrawable(App.mContext, R.drawable.selector_pop_bottom_btn));
//                        }
//                        tvItem.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (onPopItemClickListener != null) {
//                                    boolean dismiss = onPopItemClickListener.onItemClick((String) itemData, position);
//                                    if (dismiss && mBottomPop.isShowing()) {
//                                        mBottomPop.dismiss();
//                                    }
//                                }else if (mBottomPop.isShowing()) {
//                                    mBottomPop.dismiss();
//                                }
//                            }
//                        });
//                        if (position == items.size() - 1) {
//                            line.setVisibility(View.GONE);
//                        } else {
//                            line.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//        mBottomPop.findViewById(R.id.tvCancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mBottomPop.isShowing()) {
//                    mBottomPop.dismiss();
//                }
//            }
//        });
//        mBottomPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
//    }
//
//    public void showBottomPops(Activity activity, View rootView, ViewGroup dimView, Y_ItemEntityList itemEntityListItems, OnPopItemClickListener onPopItemClickListener) {
//        mBottomPop = EasyPopup.create()
//                .setContentView(activity, R.layout.logis_view_pop_bottom, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setAnimationStyle(R.style.BottomPopAnim)
//                //是否允许点击PopupWindow之外的地方消失
//                .setFocusAndOutsideEnable(true)
//                //允许背景变暗
//                .setBackgroundDimEnable(true)
//                //变暗的透明度(0-1)，0为完全透明
//                .setDimValue(0.4f)
//                //变暗的背景颜色
//                .setDimColor(Color.BLACK)
//                //指定任意 ViewGroup 背景变暗
//                .setDimView(dimView)
//                .apply();
//
//        EmptyRecyclerView rcItems = mBottomPop.findViewById(R.id.rcItems);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.mContext);
//        rcItems.setLayoutManager(linearLayoutManager);
//        Y_MultiRecyclerAdapter itemsAdapter = new Y_MultiRecyclerAdapter(activity, itemEntityListItems);
//        rcItems.setAdapter(itemsAdapter);
//        mBottomPop.findViewById(R.id.tvCancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mBottomPop.isShowing()) {
//                    mBottomPop.dismiss();
//                }
//            }
//        });
//        mBottomPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
//    }
//
    public interface OnPopItemClickListener {
        boolean onItemClick(String item, int position);
    }
//
//    public void showPhotoPop(Activity activity, String photoUrl) {
//        if (TextUtils.isEmpty(photoUrl)) {
//            return;
//        }
//        EasyPopup mPhotoPop = EasyPopup.create()
//                .setContentView(activity, R.layout.logis_activity_photo_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//                .setAnimationStyle(R.style.CenterPopAnim)
//                //是否允许点击PopupWindow之外的地方消失
//                .setFocusAndOutsideEnable(true)
//                //允许背景变暗
//                .setBackgroundDimEnable(true)
//                //变暗的透明度(0-1)，0为完全透明
//                .setDimValue(1f)
//                //变暗的背景颜色
//                .setDimColor(Color.BLACK)
//                //指定任意 ViewGroup 背景变暗
//                .setDimView((ViewGroup) activity.findViewById(android.R.id.content))
//                .apply();
//        PhotoView photoView = mPhotoPop.findViewById(R.id.photoView);
//        ImageLoadUtil.load(photoUrl, photoView, R.mipmap.transparent);
//        photoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mPhotoPop.isShowing()) {
//                    mPhotoPop.dismiss();
//                }
//            }
//        });
//        mPhotoPop.showAtLocation(activity.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
//    }

    public void dismiss() {
        if (mTopPop != null) {
            if (mTopPop.isShowing()) {
                mTopPop.dismiss();
            }
        }
        if (mBottomPop != null) {
            if (mBottomPop.isShowing()) {
                mBottomPop.dismiss();
            }
        }
    }
}
