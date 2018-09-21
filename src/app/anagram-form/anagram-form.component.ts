import { Component, OnInit } from '@angular/core';
import { Http } from "@angular/http";

@Component({
  selector: 'anagram-form',
  templateUrl: './anagram-form.component.html',
  styleUrls: ['./anagram-form.component.css']
})
export class AnagramFormComponent implements OnInit {
  public anagramString: string;
  public anagrams: string[];

  constructor(private http: Http) { }

  ngOnInit() {
  }

  findAnagrams() {
    this.http.get('/api/anagrams?phrase=' + this.anagramString)
      .map(res => res.json())
      .subscribe( json => {
        this.anagrams = json;
      });
  }

  clear() {
    this.anagramString = null;
    this.anagrams = null;
  }
}
