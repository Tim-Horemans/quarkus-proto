package org.acme;

import java.util.List;
import java.util.Optional;

import org.acme.model.Student;
import org.acme.service.StudentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;


@Path("/students")
public class StudentResource {

    @Inject
    StudentService studentService;

    @GET
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GET
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(Response::ok)
                      .orElse(Response.status(Response.Status.NOT_FOUND))
                      .build();
    }

    @POST
    public Response createStudent(Student student) {
        Student createdStudent = studentService.createStudent(student);
        return Response.status(Response.Status.CREATED).entity(createdStudent).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") Long id, Student studentDetails) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, studentDetails);
        return updatedStudent.map(Response::ok)
                             .orElse(Response.status(Response.Status.NOT_FOUND))
                             .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") Long id) {
        boolean deleted = studentService.deleteStudent(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
