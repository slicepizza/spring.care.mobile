package com.example.elderlycare.board.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elderlycare.MainActivity
import com.example.elderlycare.R
import com.example.elderlycare.board.vo.BoardVO
import com.example.elderlycare.databinding.BoardDetailBinding
import com.example.elderlycare.databinding.BoardDetailHeaderLayoutBinding
import com.example.elderlycare.ui.NavItem1Activity
import com.example.elderlycare.ui.NavItem2Activity
import com.example.ex03sqlite.util.getParcelable
import com.example.ex03sqlite.util.showNoti
import com.google.android.material.navigation.NavigationView

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: BoardDetailBinding
    private lateinit var navigationView: NavigationView
    private lateinit var navViewContainer: FrameLayout
    private var isNavViewVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.board_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.board_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding = BoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //// 네비게이션
        navigationView = findViewById(R.id.board_detail_nav_view)
        navViewContainer = findViewById(R.id.board_detail_nav_view_container)

        // 헤더 레이아웃에서 버튼과 네비게이션 뷰 컨테이너 가져오기
        val headerLayout = findViewById<RelativeLayout>(R.id.board_detail_header_layout)
        val btnMenu = headerLayout.findViewById<ImageButton>(R.id.btnMenu)
        navViewContainer = headerLayout.findViewById(R.id.board_detail_nav_view_container)

        // 네비게이션 뷰 초기화
        navigationView = findViewById(R.id.board_detail_nav_view)



//        // NavigationView 메뉴 아이템 선택 이벤트 처리
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_edit -> {
                    // nav_item1 선택 시 처리
                    startActivity(Intent(this, WriteActivity::class.java))
                }
                R.id.nav_delete -> {
                    // nav_item2 선택 시 처리
                    startActivity(Intent(this, ListActivity::class.java))
                }

                // 다른 메뉴 아이템에 대한 처리
            }
            true// true 반환하여 클릭 이벤트 소비
        }

//        // 메뉴 버튼 클릭 이벤트 처리
        btnMenu.setOnClickListener {
            toggleNavViewVisibility()
            // 네비게이션 뷰를 페이지 맨 위로 이동
            moveNavViewToTop()
        }

        val boardDetail :BoardDetailHeaderLayoutBinding = BoardDetailHeaderLayoutBinding.inflate(layoutInflater)


        var vo: BoardVO? =null

        if (intent.hasExtra("board")) {
            vo = intent.getParcelable("board", BoardVO::class.java)
        }
        if(vo == null){
            showNoti(this, "Null", "없는 데이터입니다.")
            finish()
            return
        }

        binding.tvTitle.text = vo.title
        binding.tvWriter.text = vo.writer
        binding.tvDate.text = vo.regdate.toString()
        binding.tvContents.text = vo.content
        binding.tvHitCnt.text = vo.hitcount.toString()
        binding.tvCmtCnt.text = vo.replycnt.toString()


    }

    private fun toggleNavViewVisibility() {
        val navViewContainer = findViewById<FrameLayout>(R.id.board_detail_nav_view_container)
        if (navViewContainer.visibility == View.VISIBLE) {
            // 네비게이션 뷰가 보이는 경우, 유지
            navViewContainer.visibility = View.GONE
        } else {
            // 네비게이션 뷰가 숨겨진 경우, 보이게 함
            navViewContainer.visibility = View.VISIBLE
        }
    }
    private fun moveNavViewToTop() {
        // 네비게이션 뷰를 페이지 맨 위로 이동
        ViewCompat.offsetTopAndBottom(navViewContainer, -navViewContainer.top)
    }
}