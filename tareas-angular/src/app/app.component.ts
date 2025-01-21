import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TareasComponent } from './tareas/tareas.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [TareasComponent, RouterOutlet]
})
export class AppComponent {
  title = 'tareas-angular';
}
