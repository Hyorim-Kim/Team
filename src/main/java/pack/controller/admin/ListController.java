package pack.controller.admin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pack.model.DataDao;
import pack.model.container.ContainerDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

@Controller
public class ListController {  // 리스트 목록 보게 도와주는 컨트롤러 클래스
	@Autowired
	private DataDao dataDao; //model로 감
	

	

	@GetMapping("/user") 
	public String listProcess(Model model) {
		ArrayList<UserDto> slist = (ArrayList<UserDto>)dataDao.getUserAll();
		model.addAttribute("lists", slist);
		return "../templates/user/user";
	}

	@GetMapping("/owner")
	public String listProcess2(Model model) {
		ArrayList<OwnerDto> slist2 = (ArrayList<OwnerDto>)dataDao.getOwnerAll();
		model.addAttribute("lists2", slist2);
		return "../templates/owner/owner";
	}
	

	@GetMapping("/registered")  // 등록된 창고 목록 출력
	public String listProcess3(Model model) {
		ArrayList<ContainerDto> slist3 = (ArrayList<ContainerDto>)dataDao.getConAll();
		model.addAttribute("lists3", slist3);
		return "../templates/admin/cont_registered";
	}
	

}