package ru.kpfu.itis.lifeTrack.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.service.impl.WorkflowServiceImpl;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/workflows")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Workflow")
public class WorkflowController {

    private final WorkflowServiceImpl workflowService;

    @Operation(
            operationId = "get-workflow-list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "workflow data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = WorkflowDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<?> getWorkflowList(@PathVariable(name = "userId") String userId) {
        try {
            Set<WorkflowDto> workflowDtoList = workflowService.getWorkflowList(userId);
            return new ResponseEntity<>(workflowDtoList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "get-workflow-list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "workflow data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = WorkflowDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @GetMapping(value ="{workflowId}")
    public ResponseEntity<?> getWorkflow(@PathVariable(name = "userId") String userId,
                                      @PathVariable Long workflowId) {
        try {
            return ResponseEntity.ok(workflowService.getWorkflow(userId, workflowId));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "insert-workflow",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "workflow data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = WorkflowDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertWorkflow(@PathVariable(name = "userId") String userId,
                                         @RequestBody WorkflowDto workflowRequest) {
        try {
            WorkflowDto workflow = workflowService.insertWorkflow(userId, workflowRequest);
            return ResponseEntity.ok(workflow);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(
            operationId = "patch-workflow",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "workflow data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = WorkflowDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @RequestMapping(value = "{workflowId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchWorkflow(@PathVariable(name = "userId") String userId,
                                        @PathVariable Long workflowId,
                                        @RequestBody JsonPatch patch) {
        try {
            WorkflowDto workflowDto = workflowService.patchWorkflow(userId, workflowId, patch);
            return new ResponseEntity<>(workflowDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            operationId = "update-workflow",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "workflow data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = WorkflowDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PutMapping(value = "{workflowId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateWorkflow(@PathVariable(name = "userId") String userId,
                                         @PathVariable Long workflowId,
                                         @RequestBody WorkflowDto workflowRequest) {
        try {
            WorkflowDto workflowDto = workflowService.updateWorkflow(userId, workflowId, workflowRequest);
            return new ResponseEntity<>(workflowDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "delete-workflow",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "deleted workflow id",
                            content = {
                                    @Content(
                                            mediaType = "text/plain"
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @DeleteMapping(value = "{workflowId}")
    public ResponseEntity<?> deleteWorkflow(@PathVariable(name = "userId") String userId,
                                         @PathVariable Long workflowId) {
        try {
            return new ResponseEntity<>(workflowService.deleteWorkflow(userId, workflowId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }
}
