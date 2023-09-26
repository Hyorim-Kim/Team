package pack.controller.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import pack.model.DataDao;
import pack.model.ReviewDto;
import pack.model.container.ContainerDto;
import pack.model.user.UserDto;

@Controller
public class RvInsertController {
    @Autowired
    private DataDao dataDao;

    @GetMapping("/goreview")
    public String goReviewPage(@RequestParam("cont_no") int contNo, Model model) {
        // contNo를 모델에 추가하여 Thymeleaf에서 사용할 수 있도록 함
        model.addAttribute("cont_no", contNo);
        // 다른 필요한 처리
        return "../templates/review/goreview"; // reviewPage는 HTML 페이지의 이름입니다.
    }

    
    @PostMapping("/rvinsert")
    public String insertReview(
            @RequestParam(name="cont_no", defaultValue = "0") String cont_no,
            @RequestParam(name="user_id") String user_id,
            @RequestParam(name = "reviewStar") int[] ratings,
            @RequestParam(name = "reviewContents") String[] contents,
            HttpSession session) {
    
    	// 기본값 설정
        int contNo = 0;   //사용자가 작성한 후기를 어떤 창고에 대한 것인지를 식별하기 위해 contNo 정의
        try {
            contNo = Integer.parseInt(cont_no);
        } catch (NumberFormatException e) {  // 문자열을 숫자로 변경할떄
            // cont_no가 숫자로 변환할 수 없는 경우 예외 처리
            // 기본값 0이 유지됨
        }
        
    	//System.out.println("cont_no :" + cont_no);
    	
        // 여러 후기를 처리하는 코드 작성
        for (int i = 0; i < ratings.length; i++) {
            int rating = ratings[i];
            String content = contents[i];
        
        // reviewDto 객체 생성 후 별점(rating), 내용(content)를 받아옴
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setRating(rating);
        reviewDto.setContent(content);
        reviewDto.setCont_no(contNo); // 기본값 0이 설정됨
        reviewDto.setUser_id(user_id);
        

        // 데이터베이스에 후기 저장
        //System.out.println("reviewDto : " + reviewDto);
        dataDao.saveReview(reviewDto, user_id);

        // 리뷰 저장 후 리뷰 페이지로 리다이렉트
       }
        return "redirect:/review";
    }
}
   

    /*
    // 후기 목록을 가져오는 메서드
    @GetMapping("/rvinsertAjax")
    @ResponseBody
    public String insertReviewAjax(@RequestParam("rating") int rating,
                                   @RequestParam("content") String content) {
        // reviewDto 객체 생성 후 별점(rating)과 내용(content) 설정
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setRating(rating);
        reviewDto.setContent(content);

        // 후기 저장
        dataDao.saveReviewAjax(reviewDto);

        // 클라이언트에 성공 메시지 전송
        return "후기가 저장되었습니다.";
    }
    
    
    @GetMapping("getAllReviews")
    @ResponseBody
    public List<ReviewDto> getAllReviews() {
        // 데이터베이스에서 모든 후기를 가져와 반환
        List<ReviewDto> reviews = dataDao.getAllReviews();
        return reviews;
    }
    */
    
    