/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.micode.notes.ui;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import net.micode.notes.R;

/**
 * 下拉菜单控件封装。
 *
 * 作用：将 Button + PopupMenu 组合为可复用组件，
 * 统一处理菜单加载、点击弹出、菜单项回调绑定与标题设置。
 */
public class DropdownMenu {
    private Button mButton;
    private PopupMenu mPopupMenu;
    private Menu mMenu;

    /**
     * 构造下拉菜单。
     *
     * 输入：
     * 1) context：上下文；
     * 2) button：触发菜单显示的按钮；
     * 3) menuId：菜单资源 ID（通常来自 res/menu）。
     *
     * 输出：无返回值。
     * 副作用：
     * - 为按钮设置下拉图标背景；
     * - 初始化 PopupMenu 并加载菜单；
     * - 给按钮绑定点击事件，点击后展示菜单。
     */
    public DropdownMenu(Context context, Button button, int menuId) {
        mButton = button;
        mButton.setBackgroundResource(R.drawable.dropdown_icon);
        mPopupMenu = new PopupMenu(context, mButton);
        mMenu = mPopupMenu.getMenu();
        mPopupMenu.getMenuInflater().inflate(menuId, mMenu);
        mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPopupMenu.show();
            }
        });
    }

    /**
     * 设置菜单项点击回调。
     *
     * 输入：listener 菜单项点击监听器。
     * 输出：无返回值。
     */
    public void setOnDropdownMenuItemClickListener(OnMenuItemClickListener listener) {
        if (mPopupMenu != null) {
            mPopupMenu.setOnMenuItemClickListener(listener);
        }
    }

    /**
     * 根据菜单项 ID 查找 MenuItem。
     *
     * 输入：菜单项资源 ID。
     * 输出：对应 MenuItem；找不到时返回 null。
     */
    public MenuItem findItem(int id) {
        return mMenu.findItem(id);
    }

    /**
     * 设置按钮显示标题。
     *
     * 输入：标题文本。
     * 输出：无返回值。
     */
    public void setTitle(CharSequence title) {
        mButton.setText(title);
    }
}
