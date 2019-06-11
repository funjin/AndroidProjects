package student.jnu.com.bookshelf;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static student.jnu.com.bookshelf.MainActivity.adapter;

public class BookEditActivity extends AppCompatActivity{
    private Book mBook;
    private int index;
    public static boolean isExist=false;
    //public static boolean isEdit = true;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_info_edit);
        Intent intent = getIntent();
        mBook = (Book) intent.getSerializableExtra("book_object");
        index = intent.getIntExtra("book_list_index",0);

        Toolbar toolbar = (Toolbar)findViewById(R.id.book_edit_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAlertDialog();
            }
        });

        TextView textView = (TextView) findViewById(R.id.book_edit_toolbar_save);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setview(mBook);
                //isExist = false;
                boolean isFromMain = true;

                for (Book book:DatabaseOP.getInstance().selectALLBook()){
                    if (!mBook.getISBN().equals(book.getISBN())){
                        isFromMain = false;
                    }
                }
                if (isFromMain){
                    DatabaseOP.getInstance().updateAllBook(mBook);
                    MainActivity.bookList = DatabaseOP.getInstance().selectALLBook();
                    adapter.setmBookList(MainActivity.bookList);
                    adapter.notifyDataSetChanged();
                }else {
                    getView(mBook);
                    MainActivity.addbook.set(index,mBook);
                    adapter.setmBookList(MainActivity.bookList);
                    adapter.notifyDataSetChanged();
                }
                finish();/////add
            }
        });
        setView(mBook);
    }

    public void makeAlertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(BookEditActivity.this);
        dialog.setTitle("放弃");
        dialog.setMessage("放弃这次更改?");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setView(Book book){
        editText1=(EditText)findViewById(R.id.book_pubmonth_edit_text);
        editText1.setText(book.getDate());
        editText2=(EditText)findViewById(R.id.book_pubyear_edit_text);

        editText3=(EditText)findViewById(R.id.book_website_edit_text);
        textView1=(TextView)findViewById( R.id.book_title_edit_text);
        textView1.setText(book.getName());
        textView2=(TextView)findViewById(R.id.book_author_edit_text);
        textView2.setText(book.getEditor());
        textView3=(TextView)findViewById(R.id.book_translator_edit_text);

        textView4=(TextView)findViewById(R.id.book_publisher_edit_text);
        textView4.setText(book.getPublishing());
        textView5=(TextView)findViewById(R.id.book_isbn_edit_text);
        textView5.setText(book.getISBN());
        String s=book.getDate();
        if(book.getPublishing().length()>5){
            editText1.setText(s.substring(s.length()-3,s.length()-1));
            editText2.setText(s.substring(0,4));}
        else editText2.setText(s);

        editText3.setText(book.getOuputURL());
    }

    public void getView(Book book){
        book.setDate(editText1.getText().toString());
        //book.setAddTime(editText2.getText().toString());
        book.setOuputURL(editText3.getText().toString());
        book.setTitle(textView1.getText().toString());
        book.setEditor(textView2.getText().toString());
        book.setTranslator(textView3.getText().toString());

        book.setPublishing(textView4.getText().toString());
        book.setISBN(textView5.getText().toString());
    }

}
