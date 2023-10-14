package br.art.jss.todolist.task;

import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.art.jss.todolist.utils.Utils;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        taskModel.setIdUser((UUID) request.getAttribute("idUser"));

        var currentDate = LocalDateTime.now();

        if (
               currentDate.isAfter(taskModel.getStartAt())
            || currentDate.isAfter(taskModel.getEndAt())
        ) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("A (Data de início / Data de Termino) deve ser maior que a data atual");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("A data de início deve ser menor que a data de término");
        }

        var task = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser(idUser);

        return tasks;
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);

        Utils.copyNonNullProperties(taskModel, task);

        return this.taskRepository.save(task);
    }
}
