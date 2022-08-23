import dayjs from 'dayjs/esm';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface IChat {
  id: number;
  chatDate?: dayjs.Dayjs | null;
  textMessage?: string | null;
  users?: Pick<IUserExtra, 'id'>[] | null;
}

export type NewChat = Omit<IChat, 'id'> & { id: null };
