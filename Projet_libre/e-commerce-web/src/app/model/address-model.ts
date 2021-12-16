import { User } from './user-model';

export class Address {
    id: number;
    road: string;
    postalCode: string;
    city: string;
    country: string;
    id_user: User;
}
