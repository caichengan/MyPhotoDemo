package com.an.myphotodemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.an.myphotodemo.image.ImagePagerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import me.iwf.photopicker.widget.SquareItemLayout;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private NoScrollGridView itemLayout;
    private ArrayList<String> photos;
    private List<String> photossss;
    private NinePicturesAdapter ninePicturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemLayout = (NoScrollGridView) findViewById(R.id.recycler_view);

       /* tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int photoCount = ninePicturesAdapter.getPhotoCount();
                Log.i(TAG, "---onClick:--- "+photoCount);

                if (photoCount>9){
                    Toast.makeText(MainActivity.this, "智能选择9张图片，才能提交", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < photoCount; i++) {
                    Log.i(TAG, "onClick: "+photos.get(i));
                }
                Log.i(TAG, "---提交:--- "+photoCount);

            }
        });*/


        ninePicturesAdapter = new NinePicturesAdapter(this, 9, new NinePicturesAdapter.OnClickAddListener() {
            @Override
            public void onClickAdd(int positin) {
                choosePhoto();
            }
        }, new NinePicturesAdapter.OnItemClickAddListener() {
            @Override
            public void onItemClick(int positin) {

                Log.i(TAG, "-------------onItemClick: "+positin);

                String[] array = new String[ninePicturesAdapter.getPhotoCount()];
                // List转换成数组
                for (int i = 0; i < photossss.size()-1; i++) {
                    array[i] = photossss.get(i);
                }
                //数组转换为集合
                //List<String> stringsss = Arrays.asList(array);

                Log.i(TAG, "----array:--- "+array.length);
                imageBrower(positin,array);
            }
        });
        itemLayout.setAdapter(ninePicturesAdapter);

    }

    /**
     * 每一张图片放大查看
     * @param position
     * @param urls
     */
    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        startActivity(intent);
    }


    /**
     * 开启图片选择器
     */
    private void choosePhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(9);
        intent.setShowCamera(true);
        startActivityForResult(intent, REQUEST_CODE);

        //ImageLoaderUtils.display(context,imageView,path);
    }

    private static final String TAG = "MainActivity";

    /**
     * 接受返回的图片数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                for (int i = 0; i < photos.size(); i++) {
                    Log.i(TAG, "----------onActivityResult: "+ photos.get(i));
                }
                    if(ninePicturesAdapter!=null) {
                        Log.i(TAG, "----------photossss: ========");
                        ninePicturesAdapter.addAll(photos);
                        photossss = ninePicturesAdapter.getData();
                        Log.i(TAG, "----------photossss: ========"+photossss.size());

                    }
            }
        }
    }
}
