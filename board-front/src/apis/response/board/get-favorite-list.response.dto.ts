import { FavoriteListItem } from 'types/interface';
import responseDto from '../response.dto';

export default interface GetFavoriteListResponseDto extends responseDto{
    favoriteList: FavoriteListItem[];
}
