package com.example.lab6_sol.controller;

import com.example.lab6_sol.entity.Usuario;
import com.example.lab6_sol.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/lista")
    public String listaUsuarios(Model model) {
        List<Usuario> estudiantes = usuarioRepository.findByRolid(5);
        model.addAttribute("estudiantes", estudiantes);
        return "lista_usuarios";
    }

    @GetMapping("/new")
    public String nuevoUsuario(Model model, @ModelAttribute("usuario") Usuario usuario) {

        return "newEstudiante";
    }

    @GetMapping("/edit")
    public String editarEstudiante(Model model, @RequestParam("id") int id, @ModelAttribute("usuario") Usuario usuario) {

        Optional<Usuario> optProduct = usuarioRepository.findById(id);

        if (optProduct.isPresent()) {
            usuario = optProduct.get();
            model.addAttribute("usuario ", usuario);
            return "newEstudiante";
        } else {
            return "redirect:/estudiante";
        }
    }

    @PostMapping("/save")
    public String guardarEstudiante(RedirectAttributes attr,
                                    Model model,
                                    @ModelAttribute("usuario") @Valid Usuario usuario,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "newEstudiante";
        } else {
            if (usuario.getId() == 0) {

                    attr.addFlashAttribute("msg", "usuario creado exitosamente");
                    usuario.setActivo(true);
                    usuario.setRolid(5);
                    usuarioRepository.save(usuario);
                    return "redirect:/estudiante";
            } else {
                attr.addFlashAttribute("msg", "usuario actualizado exitosamente");
                usuarioRepository.save(usuario);
                return "redirect:/estudiante";
            }
        }
    }
}
