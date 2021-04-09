package com.frist.drafting_books.ui.community;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frist.drafting_books.MainActivity;
import com.frist.drafting_books.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leancloud.AVUser;

public class Comment extends AppCompatActivity {
    private Dialog dialog;
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initComment();
        Button bt_comment=findViewById(R.id.bt_comment);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReplyDialog();
            }
        });
    }

    private void showReplyDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){


                    dialog.dismiss();
                    //这里把用户的名字传进去。
                    AVUser curUser = AVUser.getCurrentUser();
                    Map<String,String> map=new HashMap<>();
                    //等实现登录功能先。
//                    map.put("name",curUser.get("name").toString());
                    //注意，adapter用的键值是reader comment
                    map.put("reader","liyunwei");
                    map.put("comment",replyContent);

                    adapter.addTheReplyData(map);
                    Toast.makeText(Comment.this,"回复成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Comment.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    void initComment(){
        recyclerView=findViewById(R.id.comment);
        //todo 在这里传入评论列表，并且可以传入作者名字。

        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map1=new HashMap<>();
        map1.put("reader","lyw");
        map1.put("comment","版权归作者所有，任何形式转载请联系作者。\\n\" +\n" +
                "                \"作者：风君（来自豆瓣）\\n\" +\n" +
                "                \"来源：https://book.douban.com/review/5799892/\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"《人间失格》书成当年，太宰治旋即投水自尽。这部遗作，"+
                "也因此在太宰的作品之中占有举足轻重的地位，被认为是作家一生遭遇与心路历程的映射。"+"" +
                "史铁生曾说过：“写作者，未必能塑造出真实的他人，写作者只可能塑造真实的自己。”这话用在太宰治身上可谓恰如其分"+
                "，纵观他的各部作品中那诸多角色，不啻为他自己的无数分身。而在《人间失格》里，这种自我写照实在过于明显，"+
                "以至于对太宰治略有了解的读者根本无需分析，就可以看出书中主角“大庭叶藏”其实就是“津岛修治”，亦即作者本人的化身。"+
                "书中以叶藏独白道出的经历，与作者本人的人生重合度之高，令这部作品也被冠以“自传体小说”之名。"+
                "鉴于其“遗作”的特殊地位，本书可看作是太宰治本人对自己人生的某种“总结”，窥探其内心世界的最后机会。"+
                "在本书中，作者依旧一如既往地描写了一个被社会排斥的“边缘人”角色的挣扎与沉沦。"+
                "而若要问本书与太宰其它作品相比最显著的特点，或者“相同之中的些许不同”是什么的话，恐怕只能说，"+
                "本作是刻画太宰治“丑角精神”最深入、最全面也最彻底的一部作品。\\n");
        for(int i=0;i<10;i++){
            list.add(map1);
        }


        adapter=new CommentAdapter(this,list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}