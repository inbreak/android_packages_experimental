/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.loaderapp;

import com.android.loaderapp.ContactsListView.SimpleViewFactory;
import com.android.loaderapp.model.VisibleContactsLoader;

import android.app.patterns.CursorLoader;
import android.app.patterns.Loader;
import android.app.patterns.LoaderActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeNormal extends LoaderActivity<Cursor> implements OnItemClickListener {
    static final int LOADER_LIST = 1;

    ContactsListView mList;
    CursorLoader mLoader;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.normal_home);

        mList = (ContactsListView) findViewById(android.R.id.list);
        mList.setViewFactory(new SimpleViewFactory(R.layout.normal_list_item));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onInitializeLoaders() {
        startLoading(LOADER_LIST, null);
    }

    @Override
    protected Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_LIST:
                return new VisibleContactsLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadComplete(Loader loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_LIST:
                mList.setCursor(data);
                break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // The user clicked on an item in the the list, start an activity to view it
        Uri contactUri = mList.getContactUri(position);
        if (contactUri != null) {
            Intent intent = new Intent(this, DetailsNormal.class);
            intent.setData(contactUri);
            startActivity(intent);
        }
    }
}
