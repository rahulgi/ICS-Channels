/*
 * Copyright (C) 2011 The Stanford MobiSocial Laboratory
 *
 * This file is part of Musubi, a mobile social network.
 *
 *  This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package mobisocial.musubi.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class HTTPDownloadFileToExternalTask extends AsyncTask<String, Void, String> {
    @Override
    public String doInBackground(String... urls) {
        for (String url : urls) {
        	FileOutputStream out = null;
        	InputStream in = null;
            try {
                URL apkUrl = new URL(url);
                HttpURLConnection c = (HttpURLConnection) apkUrl.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                String PATH = Environment.getExternalStorageDirectory() + "/download/";
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, "app.apk");
                out = new FileOutputStream(outputFile);

                in = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len1);
                }
                return outputFile.getPath();
            } catch (IOException e) {
            } finally {
            	try {
    				if(in != null) in.close();
    				if(out != null) out.close();
    			} catch (IOException e) {
    				Log.e("HTTPDownloadtoexternal", "failed to close streams for http external", e);
    			}
            }
        }
        return null;
    }
}