package dragonfly.ormtester;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import dragonfly.butterfly.ButterflyHelper;
import dragonfly.butterfly.Schema;
import dragonfly.butterfly.Table;


public class MainActivity extends Activity {
    static Schema schema;
    static {

        schema = new Schema.Builder(

                Table.newInstance("ReferenceLocation")
                        .addColumn("Id", "INTEGER", false)
                        .addColumn("Name", "TEXT", true)
                        .addForeignKey("Specie", "Name")
                        .addColumn("Latitude", "DOUBLE", "0")
                        .addColumn("Longitude", "DOUBLE", "0")
                        .addForeignKey("Specie", "IconName")
                        .addPrimaryKey("Id")
                        .addUnique("Latitude")
                        .addUnique("Name")
                        .addCheck("Latitude > -1")
                        .addCheck("Longitude > -1"),

                Table.newInstance("Specie")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Name", "TEXT")
                        .addColumn("IconName", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Subspecie")
                        .addColumn("Id", "INTEGER")
                        .addPrimaryKey("Id"),

                Table.newInstance("Animal")
                        .addColumn("Id", "INTEGER")
                        .addForeignKey("Subspecie", "Id")
                        .addColumn("LayoutFileName", "TEXT")
                        .addColumn("Name", "TEXT")
                        .addColumn("ScientificName", "TEXT")
                        .addColumn("SubspecieId", "INTEGER")
                        .addPrimaryKey("Id"),

                Table.newInstance("Sighting")
                        .addColumn("Id", "INTEGER")
                        .addColumn("AnimalId", "INTEGER")
                        .addForeignKey("Animal", "Id")
                        .addColumn("Description", "TEXT")
                        .addColumn("Timestamp", "DATETIME")
                        .addPrimaryKey("Id"),

                Table.newInstance("Photo")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Data", "BLOB")
                        .addPrimaryKey("Id"),

                Table.newInstance("Sighting_Photo")
                        .addColumn("SightingId", "INTEGER")
                        .addForeignKey("Sighting", "Id")
                        .addColumn("PhotoId", "INTEGER")
                        .addForeignKey("Photo", "Id")
                        .addPrimaryKey("SightingId", "PhotoId"),

                Table.newInstance("Story")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Title", "TEXT")
                        .addColumn("Body", "TEXT")
                        .addColumn("Timestamp", "DATETIME")
                        .addPrimaryKey("Id"),

                Table.newInstance("Story_Photo")
                        .addColumn("StoryId", "INTEGER")
                        .addForeignKey("Story", "Id")
                        .addColumn("PhotoId", "INTEGER")
                        .addForeignKey("Photo", "Id")
                        .addPrimaryKey("StoryId", "PhotoId"),

                Table.newInstance("TopicType")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Name", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Topic")
                        .addColumn("Id", "INTEGER")
                        .addColumn("TopicTypeId", "INTEGER")
                        .addForeignKey("TopicType", "Id")
                        .addColumn("Name", "TEXT")
                        .addColumn("LayoutFileName", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Region")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Name", "TEXT")
                        .addColumn("Description", "TEXT")
                        .addColumn("LayoutFilename", "TEXT")
                        .addColumn("Latitude", "DOUBLE")
                        .addColumn("Longitude", "DOUBLE")
                        .addColumn("MapPinOrder", "INTEGER")
                        .addPrimaryKey("Id"),

                Table.newInstance("Activity")
                        .addColumn("Id", "INTEGER")
                        .addColumn("RegionId", "INTEGER")
                        .addForeignKey("Region", "Id")
                        .addColumn("Description", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Animal_Region")
                        .addColumn("AnimalId", "INTEGER")
                        .addForeignKey("Animal", "Id")
                        .addColumn("RegionId", "INTEGER")
                        .addForeignKey("Region", "Id")
                        .addPrimaryKey("AnimalId", "RegionId"),

                Table.newInstance("Region_Topic")
                        .addColumn("RegionId", "INTEGER")
                        .addForeignKey("Region", "Id")
                        .addColumn("TopicId", "INTEGER")
                        .addForeignKey("Topic", "Id")
                        .addPrimaryKey("RegionId", "TopicId"),

                Table.newInstance("Column")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Width", "DOUBLE")
                        .addColumn("Spacing", "DOUBLE")
                        .addPrimaryKey("Id"),

                Table.newInstance("ContentType")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Name", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Content")
                        .addColumn("ContentTypeId", "INTEGER")
                        .addForeignKey("ContentType", "Id")
                        .addColumn("ContentId", "INTEGER")
                        .addPrimaryKey("ContentTypeId", "ContentId"),

                Table.newInstance("SubjectType")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Name", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Subject")
                        .addColumn("SubjectTypeId", "INTEGER")
                        .addForeignKey("SubjectType", "Id")
                        .addColumn("SubjectId", "INTEGER")
                        .addPrimaryKey("SubjectTypeId", "SubjectId"),

                Table.newInstance("Document")
                        .addColumn("Id", "INTEGER")
                        .addColumn("SubjectTypeId", "INTEGER")
                        .addForeignKey("Subject", "SubjectTypeId")
                        .addColumn("SubjectId", "INTEGER")
                        .addForeignKey("Subject", "SubjectId")
                        .addColumn("ColumnId", "INTEGER")
                        .addForeignKey("Column", "Id")
                        .addColumn("LeftGutter", "DOUBLE")
                        .addColumn("TitleBaselinePosition", "DOUBLE")
                        .addColumn("LeadingWidth", "DOUBLE")
                        .addColumn("TopPosition", "DOUBLE")
                        .addColumn("ContentHeight", "DOUBLE")
                        .addColumn("VideoPadding", "DOUBLE")
                        .addPrimaryKey("Id"),

                Table.newInstance("Section")
                        .addColumn("Id", "INTEGER")
                        .addColumn("DocumentId", "INTEGER")
                        .addForeignKey("Document", "Id")
                        .addColumn("ContentTypeId", "INTEGER")
                        .addForeignKey("Content", "ContentTypeId")
                        .addColumn("ContentId", "INTEGER")
                        .addForeignKey("Content", "ContentId")
                        .addColumn("Type", "TEXT")
                        .addColumn("Text", "TEXT")
                        .addColumn("Columns", "INTEGER")
                        .addColumn("Overlap", "DOUBLE")
                        .addColumn("Alignment", "TEXT")
                        .addColumn("HyphenationFactor", "DOUBLE")
                        .addPrimaryKey("Id"),

                Table.newInstance("Image")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Filename", "TEXT")
                        .addColumn("FileExtension", "TEXT")
                        .addColumn("Credit", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Text")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Filename", "TEXT")
                        .addColumn("FileExtension", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Video")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Filename", "TEXT")
                        .addColumn("FileExtension", "TEXT")
                        .addColumn("Credit", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Figure")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Filename", "TEXT")
                        .addColumn("FileExtension", "TEXT")
                        .addPrimaryKey("Id"),

                Table.newInstance("Map")
                        .addColumn("Id", "INTEGER")
                        .addColumn("Filename", "TEXT")
                        .addColumn("FileExtension", "TEXT")
                        .addPrimaryKey("Id")

        ).build();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(this.getClass().getName(), ButterflyHelper.generateDatabaseSql(schema));
        //Log.d(this.getClass().getName(), ButterflyHelper.generateDatabaseSql((Schema)schema.clone()));

        SQLiteDatabase sqLiteDatabase = DatabaseManager.openDatabase(this, schema);
        ButterflyHelper.insert(sqLiteDatabase, new Object[]{});
    }
}
