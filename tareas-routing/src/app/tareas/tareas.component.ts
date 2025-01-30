import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tareas',
  templateUrl: './tareas.component.html',
  styleUrls: ['./tareas.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule] // Importa los módulos necesarios
})
export class TareasComponent {
  listaTareas: { nombre: string; completada: boolean }[] = [];
  nuevaTarea: string = '';

  agregarTarea() {
    if (this.nuevaTarea.trim()) {
      this.listaTareas.push({ nombre: this.nuevaTarea.trim(), completada: false });
      this.nuevaTarea = '';
    }
  }

  toggleTarea(tarea: { nombre: string; completada: boolean }) {
    tarea.completada = !tarea.completada;
  }

  eliminarTarea(tarea: { nombre: string; completada: boolean }) {
    this.listaTareas = this.listaTareas.filter(t => t !== tarea);
  }
}