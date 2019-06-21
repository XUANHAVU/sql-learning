import {Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import {ICategory} from "app/shared/model/category.model";

@Pipe({name: 'safe'})
export class SafePipe implements PipeTransform {
    constructor(private sanitizer: DomSanitizer) {
    }

    transform(url) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }
}

@Pipe({name: 'highlight'})
export class Highlight implements PipeTransform {

    // transform(value: any, args: any): any {
    //     if (!args) {
    //         return value;
    //     }
    //     const re = new RegExp(args, 'gi'); // 'gi' for case insensitive and can use 'g' if you want the search to be case sensitive.
    //     return value.replace(re, '<span class="text-primary">' + args + '</span>');
    // }

    transform(value: string, args: string[]): any {
        if (args && value) {
            value = String(value); // make sure its a string
            for (let i = 0; i < args.length; i++) {
                const startIndex = value.indexOf(args[i]);
                if (startIndex !== -1) {
                    const matchingString = value.substr(startIndex, args[i].length);
                    value = value.replace(matchingString, '<span class="text-primary">' + args[i] + '</span>');
                }
            }
        }
        return value;
    }

//     const startIndex = value.indexOf(args[i]);
//     if (startIndex !== -1) {
//     const matchingString = value.substr(startIndex, args[i].length);
//     return value.replace(matchingString, '<span class="text-primary">' + matchingString + '</span>');
// }
}

@Pipe({name: 'keys'})
export class KeysPipe implements PipeTransform {
    transform(value, args: string[]): any {
        let keys = [];
        for (let key in value) {
            keys.push(key);
        }
        keys = keys.sort((x, y) => {
            return x.length - y.length;
        });
        return keys;
    }
}

@Pipe({name: 'substring'})
export class SubString implements PipeTransform {
    transform(value: string): any {
        let str = '';
        if (value) {
            value = String(value); // make sure its a string
            const splitted = value.split('. ');
            console.log(splitted);
            if (splitted[splitted.length - 1].includes('.')) {
                splitted[splitted.length - 1] = splitted[splitted.length - 1].replace('.', '');
            }

            for (let i = 0; i < splitted.length; i++) {
                str = str + splitted[i].replace(
                    splitted[i],
                    '<li>' + ' ' + splitted[i] + '.</li>');
            }

        }
        return str;
    }
}

@Pipe({name: 'filter'})
export class FilterPipe implements PipeTransform {
    transform(items: ICategory[], searchText: string): any[] {

        if (!items) {
            return [];
        }
        if (!searchText) {
            return items;
        }
        searchText = searchText.toLocaleLowerCase();

        return items.filter(it => {
            return it.nameCategory.toLocaleLowerCase().includes(searchText);
        });
    }
}
