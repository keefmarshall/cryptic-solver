import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-word-list',
  templateUrl: './word-list.component.html',
  styleUrls: ['./word-list.component.css']
})
export class WordListComponent implements OnInit {
  @Input() items

  constructor() { }

  ngOnInit() {
  }

}
