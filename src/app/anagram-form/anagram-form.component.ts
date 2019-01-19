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
  public anagramSub: boolean = false;

  constructor(private http: Http) { }

  ngOnInit() {
  }

  findAnagrams() {
    const url = this.generateUrl();

    this.http.get(url)
      .map(res => res.json())
      .subscribe( json => {
        this.anagrams = json;
      });
  }

  private generateUrl(): string {
    if (this.anagramSub) {
      return `/api/anagramsub?phrase=${this.anagramString}&minLength=3`;
    } else {
      return '/api/anagrams?phrase=' + this.anagramString;
    }
  }

  clear() {
    this.anagramString = null;
    this.anagrams = null;
  }
}
