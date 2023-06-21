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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.request.ProjectRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.ProjectResponseDto;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.security.jwt.JwtUserDetails;
import ru.kpfu.itis.lifeTrack.service.ProjectService;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/workflows/{workflowId}/projects")
@RequiredArgsConstructor
@Tag(name = "Project")
@PreAuthorize("isAuthenticated()")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(
            operationId = "get-project-list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "project data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ProjectResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @GetMapping(value = "")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> getProjectsList(@AuthenticationPrincipal JwtUserDetails userDetails,
                                             @PathVariable(name = "userId") String userId,
                                             @PathVariable(name = "workflowId") Long workflowId) throws NotFoundException {
        Set<ProjectResponseDto> projectEntitySet = projectService.getProjectList(userId, workflowId);
        return new ResponseEntity<>(projectEntitySet, HttpStatus.OK);
    }

    @Operation(
            operationId = "get-project",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "project data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ProjectResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @GetMapping(value = "{projectId}")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> getProject(@AuthenticationPrincipal JwtUserDetails userDetails,
                                        @PathVariable(name = "userId") String userId,
                                        @PathVariable(name = "workflowId") Long workflowId,
                                        @PathVariable Long projectId) throws NotFoundException {
        return ResponseEntity.ok(projectService.getProject(userId, workflowId, projectId));
    }

    @Operation(
            operationId = "insert-project",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "project data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ProjectResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PostMapping(consumes = {"application/json"})
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> insertProject(@AuthenticationPrincipal JwtUserDetails userDetails,
                                           @PathVariable(name = "userId") String userId,
                                           @PathVariable(name = "workflowId") Long workflowId,
                                           @RequestBody ProjectRequestDto projectRequest) throws NotFoundException {
        ProjectResponseDto projectDto = projectService.insertProject(userId, workflowId, projectRequest);
        return ResponseEntity.ok(projectDto);
    }

    @Operation(
            operationId = "patch-project",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "project data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ProjectResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @RequestMapping(value = "{projectId}", method = RequestMethod.PATCH, consumes = "application/json")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> patchProject(@AuthenticationPrincipal JwtUserDetails userDetails,
                                          @PathVariable(name = "userId") String userId,
                                          @PathVariable(name = "workflowId") Long workflowId,
                                          @PathVariable Long projectId,
                                          @RequestBody JsonPatch patch) throws JsonPatchException, NotFoundException, JsonProcessingException {
            ProjectResponseDto projectDto = projectService.patchProject(userId, workflowId, projectId, patch);
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @Operation(
            operationId = "update-project",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "project data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ProjectResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PutMapping(value = "{projectId}", consumes = "application/json")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> updateProject(@AuthenticationPrincipal JwtUserDetails userDetails,
                                           @PathVariable(name = "userId") String userId,
                                           @PathVariable(name = "workflowId") Long workflowId,
                                           @PathVariable Long projectId,
                                           @RequestBody ProjectRequestDto projectRequest) throws NotFoundException {
            ProjectResponseDto projectDto = projectService.updateProject(userId, workflowId, projectId,  projectRequest);
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @Operation(
            operationId = "delete-project",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "deleted project id",
                            content = {
                                    @Content(
                                            mediaType = "text/plain"
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user/workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @DeleteMapping(value = "{projectId}")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> deleteProject(@AuthenticationPrincipal JwtUserDetails userDetails,
                                           @PathVariable(name = "userId") String userId,
                                           @PathVariable(name = "workflowId") Long workflowId,
                                           @PathVariable Long projectId) throws NotFoundException {
        return new ResponseEntity<>(projectService.deleteProject(userId, workflowId, projectId), HttpStatus.OK);
    }
}
