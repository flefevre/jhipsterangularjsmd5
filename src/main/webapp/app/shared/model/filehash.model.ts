export interface IFilehash {
    id?: number;
    name?: string;
    hashOfFile?: string;
    contentOfFileContentType?: string;
    contentOfFile?: any;
}

export class Filehash implements IFilehash {
    constructor(
        public id?: number,
        public name?: string,
        public hashOfFile?: string,
        public contentOfFileContentType?: string,
        public contentOfFile?: any
    ) {}
}
