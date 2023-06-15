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
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.request.ProjectRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.ProjectResponseDto;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.service.ProjectService;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/workflows/{workflowId}/projects")
@RequiredArgsConstructor
@Tag(name = "Project")
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
    public ResponseEntity<?> getProjectsList(@PathVariable(name = "userId") String userId,
                                         @PathVariable(name = "workflowId") Long workflowId) {
        try {
            Set<ProjectResponseDto> projectEntitySet = projectService.getProjectList(userId, workflowId);
            return new ResponseEntity<>(projectEntitySet, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> getProject(@PathVariable(name = "userId") String userId,
                                     @PathVariable(name = "workflowId") Long workflowId,
                                     @PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(projectService.getProject(userId, workflowId, projectId));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> insertProject(@PathVariable(name = "userId") String userId,
                                        @PathVariable(name = "workflowId") Long workflowId,
                                        @RequestBody ProjectRequestDto projectRequest) {
        try {
            ProjectResponseDto projectDto = projectService.insertProject(userId, workflowId, projectRequest);
            return ResponseEntity.ok(projectDto);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> patchProject(@PathVariable(name = "userId") String userId,
                                       @PathVariable(name = "workflowId") Long workflowId,
                                       @PathVariable Long projectId,
                                       @RequestBody JsonPatch patch) {
        try {
            ProjectResponseDto projectDto = projectService.patchProject(userId, workflowId, projectId, patch);
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> updateProject(@PathVariable(name = "userId") String userId,
                                        @PathVariable(name = "workflowId") Long workflowId,
                                        @PathVariable Long projectId,
                                        @RequestBody ProjectRequestDto projectRequest) {
        try {
            ProjectResponseDto projectDto = projectService.updateProject(userId, workflowId, projectId,  projectRequest);
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> deleteProject(@PathVariable(name = "userId") String userId,
                                        @PathVariable(name = "workflowId") Long workflowId,
                                        @PathVariable Long projectId) {
        try {
            return new ResponseEntity<>(projectService.deleteProject(userId, workflowId, projectId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }
}
