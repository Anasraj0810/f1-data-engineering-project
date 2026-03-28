// ORM class for table 'anas.constructors_full_v'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri Mar 27 16:54:44 UTC 2026
// For connector: org.apache.sqoop.manager.GenericJdbcManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.apache.sqoop.lib.JdbcWritableBridge;
import org.apache.sqoop.lib.DelimiterSet;
import org.apache.sqoop.lib.FieldFormatter;
import org.apache.sqoop.lib.RecordParser;
import org.apache.sqoop.lib.BooleanParser;
import org.apache.sqoop.lib.BlobRef;
import org.apache.sqoop.lib.ClobRef;
import org.apache.sqoop.lib.LargeObjectLoader;
import org.apache.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class anas_constructors_full_v extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  public static interface FieldSetterCommand {    void setField(Object value);  }  protected ResultSet __cur_result_set;
  private Map<String, FieldSetterCommand> setters = new HashMap<String, FieldSetterCommand>();
  private void init0() {
    setters.put("constructorid", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_constructors_full_v.this.constructorid = (Integer)value;
      }
    });
    setters.put("constructorref", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_constructors_full_v.this.constructorref = (String)value;
      }
    });
    setters.put("name", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_constructors_full_v.this.name = (String)value;
      }
    });
    setters.put("nationality", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_constructors_full_v.this.nationality = (String)value;
      }
    });
    setters.put("url", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_constructors_full_v.this.url = (String)value;
      }
    });
  }
  public anas_constructors_full_v() {
    init0();
  }
  private Integer constructorid;
  public Integer get_constructorid() {
    return constructorid;
  }
  public void set_constructorid(Integer constructorid) {
    this.constructorid = constructorid;
  }
  public anas_constructors_full_v with_constructorid(Integer constructorid) {
    this.constructorid = constructorid;
    return this;
  }
  private String constructorref;
  public String get_constructorref() {
    return constructorref;
  }
  public void set_constructorref(String constructorref) {
    this.constructorref = constructorref;
  }
  public anas_constructors_full_v with_constructorref(String constructorref) {
    this.constructorref = constructorref;
    return this;
  }
  private String name;
  public String get_name() {
    return name;
  }
  public void set_name(String name) {
    this.name = name;
  }
  public anas_constructors_full_v with_name(String name) {
    this.name = name;
    return this;
  }
  private String nationality;
  public String get_nationality() {
    return nationality;
  }
  public void set_nationality(String nationality) {
    this.nationality = nationality;
  }
  public anas_constructors_full_v with_nationality(String nationality) {
    this.nationality = nationality;
    return this;
  }
  private String url;
  public String get_url() {
    return url;
  }
  public void set_url(String url) {
    this.url = url;
  }
  public anas_constructors_full_v with_url(String url) {
    this.url = url;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof anas_constructors_full_v)) {
      return false;
    }
    anas_constructors_full_v that = (anas_constructors_full_v) o;
    boolean equal = true;
    equal = equal && (this.constructorid == null ? that.constructorid == null : this.constructorid.equals(that.constructorid));
    equal = equal && (this.constructorref == null ? that.constructorref == null : this.constructorref.equals(that.constructorref));
    equal = equal && (this.name == null ? that.name == null : this.name.equals(that.name));
    equal = equal && (this.nationality == null ? that.nationality == null : this.nationality.equals(that.nationality));
    equal = equal && (this.url == null ? that.url == null : this.url.equals(that.url));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof anas_constructors_full_v)) {
      return false;
    }
    anas_constructors_full_v that = (anas_constructors_full_v) o;
    boolean equal = true;
    equal = equal && (this.constructorid == null ? that.constructorid == null : this.constructorid.equals(that.constructorid));
    equal = equal && (this.constructorref == null ? that.constructorref == null : this.constructorref.equals(that.constructorref));
    equal = equal && (this.name == null ? that.name == null : this.name.equals(that.name));
    equal = equal && (this.nationality == null ? that.nationality == null : this.nationality.equals(that.nationality));
    equal = equal && (this.url == null ? that.url == null : this.url.equals(that.url));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.constructorid = JdbcWritableBridge.readInteger(1, __dbResults);
    this.constructorref = JdbcWritableBridge.readString(2, __dbResults);
    this.name = JdbcWritableBridge.readString(3, __dbResults);
    this.nationality = JdbcWritableBridge.readString(4, __dbResults);
    this.url = JdbcWritableBridge.readString(5, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.constructorid = JdbcWritableBridge.readInteger(1, __dbResults);
    this.constructorref = JdbcWritableBridge.readString(2, __dbResults);
    this.name = JdbcWritableBridge.readString(3, __dbResults);
    this.nationality = JdbcWritableBridge.readString(4, __dbResults);
    this.url = JdbcWritableBridge.readString(5, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(constructorid, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(constructorref, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(name, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(nationality, 4 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(url, 5 + __off, 12, __dbStmt);
    return 5;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(constructorid, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(constructorref, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(name, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(nationality, 4 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(url, 5 + __off, 12, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.constructorid = null;
    } else {
    this.constructorid = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.constructorref = null;
    } else {
    this.constructorref = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.name = null;
    } else {
    this.name = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.nationality = null;
    } else {
    this.nationality = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.url = null;
    } else {
    this.url = Text.readString(__dataIn);
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.constructorid) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.constructorid);
    }
    if (null == this.constructorref) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, constructorref);
    }
    if (null == this.name) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, name);
    }
    if (null == this.nationality) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, nationality);
    }
    if (null == this.url) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, url);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.constructorid) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.constructorid);
    }
    if (null == this.constructorref) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, constructorref);
    }
    if (null == this.name) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, name);
    }
    if (null == this.nationality) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, nationality);
    }
    if (null == this.url) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, url);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(constructorid==null?"null":"" + constructorid, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(constructorref==null?"null":constructorref, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(name==null?"null":name, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(nationality==null?"null":nationality, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(url==null?"null":url, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(constructorid==null?"null":"" + constructorid, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(constructorref==null?"null":constructorref, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(name==null?"null":name, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(nationality==null?"null":nationality, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(url==null?"null":url, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.constructorid = null; } else {
      this.constructorid = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.constructorref = null; } else {
      this.constructorref = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.name = null; } else {
      this.name = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.nationality = null; } else {
      this.nationality = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.url = null; } else {
      this.url = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.constructorid = null; } else {
      this.constructorid = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.constructorref = null; } else {
      this.constructorref = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.name = null; } else {
      this.name = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.nationality = null; } else {
      this.nationality = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.url = null; } else {
      this.url = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    anas_constructors_full_v o = (anas_constructors_full_v) super.clone();
    return o;
  }

  public void clone0(anas_constructors_full_v o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new HashMap<String, Object>();
    __sqoop$field_map.put("constructorid", this.constructorid);
    __sqoop$field_map.put("constructorref", this.constructorref);
    __sqoop$field_map.put("name", this.name);
    __sqoop$field_map.put("nationality", this.nationality);
    __sqoop$field_map.put("url", this.url);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("constructorid", this.constructorid);
    __sqoop$field_map.put("constructorref", this.constructorref);
    __sqoop$field_map.put("name", this.name);
    __sqoop$field_map.put("nationality", this.nationality);
    __sqoop$field_map.put("url", this.url);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if (!setters.containsKey(__fieldName)) {
      throw new RuntimeException("No such field:"+__fieldName);
    }
    setters.get(__fieldName).setField(__fieldVal);
  }

}
