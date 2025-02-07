package pe.edu.vallegrande.vgmsstudyprogramme.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsstudyprogramme.application.service.StudyProgramService;
import pe.edu.vallegrande.vgmsstudyprogramme.domain.dto.Profile;
import pe.edu.vallegrande.vgmsstudyprogramme.domain.dto.StudyProgramCreateDto;
import pe.edu.vallegrande.vgmsstudyprogramme.domain.dto.StudyProgramUpdateDto;
import pe.edu.vallegrande.vgmsstudyprogramme.domain.dto.StudyProgramIdsDto;
import pe.edu.vallegrande.vgmsstudyprogramme.domain.model.StudyProgram;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/common/${api.version}/study-program")
public class StudyProgramController {

    private final StudyProgramService studyProgramServiceImpl;

    @Autowired
    public StudyProgramController(StudyProgramService studyProgramServiceImpl) {
        this.studyProgramServiceImpl = studyProgramServiceImpl;
    }

    @GetMapping("/profile")
    public Flux<Profile> testProfileClient() {
        return studyProgramServiceImpl.listActive();
    }

    @GetMapping("/list/active")
    public ResponseEntity<Flux<StudyProgram>> listActive() {
        return ResponseEntity.ok(studyProgramServiceImpl.getByStatus("A"));
    }

    @GetMapping("/list/inactive")
    public ResponseEntity<Flux<StudyProgram>> listInactive() {
        return ResponseEntity.ok(studyProgramServiceImpl.getByStatus("I"));
    }

    @PostMapping("/create")
    public Mono<StudyProgram> create(@RequestBody StudyProgramCreateDto studyProgramCreateDto) {
        return studyProgramServiceImpl.create(studyProgramCreateDto);
    }

    @PutMapping("/update/{id}")
    public Mono<StudyProgram> update(@PathVariable("id") String programId,
                                     @RequestBody StudyProgramUpdateDto studyProgramUpdateDto) {
        return studyProgramServiceImpl.update(programId, studyProgramUpdateDto);
    }



    @PutMapping("/activate/{id}")
    public ResponseEntity<Mono<StudyProgram>> activate(@PathVariable("id") String programId) {
        return ResponseEntity.ok(studyProgramServiceImpl.changeStatus(programId, "A"));
    }

    @PutMapping("/inactive/{id}")
    public ResponseEntity<Mono<StudyProgram>> deactivate(@PathVariable("id") String programId) {
        return ResponseEntity.ok(studyProgramServiceImpl.changeStatus(programId, "I"));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Mono<Profile>> getCetproProfile(@PathVariable("id") String cetproId) {
        return ResponseEntity.ok(studyProgramServiceImpl.getCetproProfile(cetproId));
    }

    @GetMapping("/{id}")
    public Mono<StudyProgram> getById(@PathVariable("id") String programId) {
        return studyProgramServiceImpl.getById(programId);
    }

    @GetMapping("/cetpro/{cetproId}")
    public ResponseEntity<Flux<StudyProgram>> getProgramsByCetpro(@PathVariable String cetproId) {
        return ResponseEntity.ok(studyProgramServiceImpl.getByCetproId(cetproId));
    }

    @PostMapping("/{cetproId}/programs")
    public Flux<StudyProgram> assignProgramsToCetpro(@PathVariable String cetproId, @RequestBody StudyProgramIdsDto studyProgramIdsDto) {
        return studyProgramServiceImpl.assignProgramsToCetpro(cetproId, studyProgramIdsDto);
    }

}