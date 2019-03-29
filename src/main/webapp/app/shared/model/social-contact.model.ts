export const enum SocialNetwork {
    FACEBOOK = 'FACEBOOK',
    TWITTER = 'TWITTER',
    INSTAGRAM = 'INSTAGRAM',
    LINKEDIN = 'LINKEDIN',
    YOUTUBE = 'YOUTUBE'
}

export interface ISocialContact {
    id?: number;
    socialNetwork?: SocialNetwork;
    link?: string;
    eBCardId?: number;
}

export class SocialContact implements ISocialContact {
    constructor(public id?: number, public socialNetwork?: SocialNetwork, public link?: string, public eBCardId?: number) {}
}
