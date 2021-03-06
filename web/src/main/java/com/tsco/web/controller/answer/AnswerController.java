package com.tsco.web.controller.answer;

import com.tsco.answer.domain.po.Subject;
import com.tsco.answer.domain.vo.AnswerVo;
import com.tsco.answer.service.AnswerService;
import com.tsco.api.utils.StringUtils;
import com.tsco.web.config.annotations.Interceptor;
import com.tsco.web.controller.BaseController;
import com.tsco.web.domain.Response;
import com.tsco.web.domain.vo.SubjectVo;
import com.tsco.web.domain.vo.SubmitAnswerForm;
import com.tsco.web.exception.ExceptionCode;
import com.tsco.web.exception.WebException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@Api(description = "答题相关接口", tags = "answer")
@RestController
@RequestMapping("/answer")
public class AnswerController extends BaseController {

    @Resource(name = "answerServiceImpl")
    private AnswerService answerService;


    @ApiOperation(value = "提交答案", notes = "submit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectId", value = "题目id", paramType = "Inteager"),
            @ApiImplicitParam(name = "answer", value = "答案", paramType = "String"),
            @ApiImplicitParam(name = "subjectType", value = "题目类型", paramType = "String")
    })
    @PostMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response<AnswerVo> submit(@RequestBody SubmitAnswerForm submitAnswerForm) {
        if (submitAnswerForm.getSubjectId() == null || StringUtils.isNullOrEmpty(submitAnswerForm.getAnswer()) || submitAnswerForm.getUserId() == null) {
            throw new WebException(ExceptionCode.INVALID_PARAMETER, "参数不能为空");
        }
        AnswerVo answerVo = answerService.submit(submitAnswerForm.getUserId(), submitAnswerForm.getSubjectId(), submitAnswerForm.getAnswer());
        return Response.SUCCESS(answerVo);
    }

    @ApiOperation(value = "题目详情", notes = "subject-detail")
    @ApiImplicitParam(name = "id", value = "题目id", paramType = "Integer")
    @GetMapping(value = "/get-subject-detail")
    public Response<SubjectVo> getSubjectDetail(@RequestParam("id") Integer subjectId) {
        if (subjectId == null) {
            throw new WebException(ExceptionCode.INVALID_PARAMETER, "参数不能为空");
        }
        Subject subject = answerService.getSubjectById(subjectId);
        SubjectVo subjectVo = SubjectVo.builder()
                .id(subject.getId())
                .title(subject.getTitle())
                .content(Arrays.asList(subject.getContent().split("@")))
                .type(subject.getType().name())
                .build();
        return Response.SUCCESS(subjectVo);
    }
}


